package com.winsigns.investment.inventoryService.capital.common;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.winsigns.investment.inventoryService.command.CreateCapitalDetailCommand;
import com.winsigns.investment.inventoryService.command.CreateFundAccountCapitalPoolCommand;
import com.winsigns.investment.inventoryService.command.SetInvestmentLimitCommand;
import com.winsigns.investment.inventoryService.command.TransferBetweenFAAndECACommand;
import com.winsigns.investment.inventoryService.command.TransferBetweenFAAndFACommand;
import com.winsigns.investment.inventoryService.constant.ExternalCapitalAccountType;
import com.winsigns.investment.inventoryService.model.CapitalDetail;
import com.winsigns.investment.inventoryService.model.ECACashPool;
import com.winsigns.investment.inventoryService.model.FAToECACapitalSerial;
import com.winsigns.investment.inventoryService.model.FAToFACapitalSerial;
import com.winsigns.investment.inventoryService.model.FundAccountCapitalPool;
import com.winsigns.investment.inventoryService.repository.FundAccountCapitalPoolRepository;
import com.winsigns.investment.inventoryService.service.CapitalDetailService;
import com.winsigns.investment.inventoryService.service.CapitalSerialService;
import com.winsigns.investment.inventoryService.service.ECACashPoolService;

@Component
public class CapitalServiceManager {

  List<ICapitalService> services = new ArrayList<ICapitalService>();

  @Autowired
  FundAccountCapitalPoolRepository capitalPoolRepository;

  @Autowired
  CapitalDetailService capitalDetailService;

  @Autowired
  CapitalSerialService capitalSerialService;

  @Autowired
  ECACashPoolService ecaCashPoolService;

  /**
   * 将资金服务注册到该管理者中
   * 
   * @param service
   */
  public void register(ICapitalService service) {
    services.add(service);
  }

  /**
   * 获取名字的资金服务
   * 
   * @param name 持仓服务
   * @return 指定持仓服务
   */
  public ICapitalService getService(String name) {
    for (ICapitalService service : services) {
      if (service.getName().equals(name)) {
        return service;
      }
    }
    return null;
  }

  /**
   * 获取指定账户类型的资金服务
   * 
   * @param accountType 账户类型
   * @return
   */
  public ICapitalService getService(ExternalCapitalAccountType accountType) {
    for (ICapitalService service : services) {
      if (service.getAccountType().equals(accountType)) {
        return service;
      }
    }
    return null;
  }

  public List<FundAccountCapitalPool> findCapitalPoolsByFundAccount(Long fundAccountId) {
    return capitalPoolRepository.findByFundAccountId(fundAccountId);
  }

  /**
   * 获取指定的产品账户资金池
   * 
   * @param faCapitalPoolId
   * @return
   */
  public FundAccountCapitalPool readFundAccountCapitalPool(Long faCapitalPoolId) {
    return capitalPoolRepository.findOne(faCapitalPoolId);
  }

  /**
   * 创建一个指定类型的产品账户资金池
   * 
   * @param command
   * @return
   */
  public FundAccountCapitalPool createFundAccountCapitalPool(
      CreateFundAccountCapitalPoolCommand command) {
    return this.getService(command.getAccountType()).createFundAccountCapitalPool(command);
  }

  /**
   * 设置投资限额
   * 
   * @param command
   * @return
   */
  public FundAccountCapitalPool setInvestmentLimit(SetInvestmentLimitCommand command) {
    Assert.notNull(command.getFaCapitalPoolId());
    FundAccountCapitalPool capitalPool =
        capitalPoolRepository.findOne(command.getFaCapitalPoolId());
    Assert.notNull(capitalPool);
    // TODO 与已占用投资限额需要比较
    capitalPool.setInvestmentLimit(command.getInvestmentLimit());
    return capitalPoolRepository.save(capitalPool);
  }

  /**
   * 从产品账户转出资金到外部资金账户
   * 
   * @param command
   * @return
   */
  @Transactional
  public FundAccountCapitalPool transferFromFAToECA(TransferBetweenFAAndECACommand command) {

    Long ecaCashPoolId = command.getEcaCashPoolId();
    Long faCapitalPoolId = command.getFaCapitalPoolId();
    Double occurAmount = Math.abs(command.getOccurAmount());
    Assert.notNull(ecaCashPoolId);
    Assert.notNull(faCapitalPoolId);

    FundAccountCapitalPool capitalPool = readFundAccountCapitalPool(faCapitalPoolId);
    Assert.notNull(capitalPool);

    ECACashPool ecaCashPool = ecaCashPoolService.findECACashPool(ecaCashPoolId);
    Assert.notNull(ecaCashPool);

    Assert.isTrue(capitalPool.getCurrency().equals(ecaCashPool.getCurrency()));

    ecaCashPool.changeUnassignedCapital(occurAmount);

    CapitalDetail capitalDetail = capitalDetailService.readCapitalDetail(capitalPool, ecaCashPool);
    if (capitalDetail == null) {
      CreateCapitalDetailCommand crtCommand = new CreateCapitalDetailCommand();
      crtCommand.setFaCapitalPoolId(faCapitalPoolId);
      crtCommand.setEcaCashPoolId(ecaCashPoolId);
      capitalDetail = capitalDetailService.addFundAccountCapitalDetail(crtCommand);
    }
    capitalDetail.changeCash(-occurAmount);
    capitalDetail.changeAvailableCapital(-occurAmount);
    capitalDetail.changeDesirableCapital(-occurAmount);
    capitalDetailService.save(capitalDetail);

    capitalSerialService.addCapitalSerial(FAToECACapitalSerial.class, faCapitalPoolId,
        ecaCashPoolId, capitalPool.getCurrency(), occurAmount);

    return capitalPool;
  }

  /**
   * 从产品账户转到另一个产品账户
   * <p>
   * 需求规格说明书1.9.2.13外部资金账户.html中<br>
   * 投资经理可将其管理的基金产品账户中的分配资金让渡给其他基金产品账户， <br>
   * 由于基金产品账户的分配资金可能来自于多个资金账户， <br>
   * 因此，资金账户可能会涉及多个资金账户，分配时按照分配资金块从小到大的方式逐块划转。
   * 
   * @param command
   * @return
   */
  @Transactional
  public FundAccountCapitalPool transferFromFAToFA(TransferBetweenFAAndFACommand command) {
    Long srcFACapitalPoolId = command.getSrcFACapitalPoolId();
    Long dstFACapitalPoolId = command.getDstFACapitalPoolId();
    Double occurAmount = Math.abs(command.getOccurAmount());
    Assert.notNull(srcFACapitalPoolId);
    Assert.notNull(dstFACapitalPoolId);

    FundAccountCapitalPool srcCapitalPool = this.readFundAccountCapitalPool(srcFACapitalPoolId);
    Assert.notNull(srcCapitalPool);

    FundAccountCapitalPool dstCapitalPool = this.readFundAccountCapitalPool(dstFACapitalPoolId);
    Assert.notNull(dstCapitalPool);
    Assert.isTrue(srcCapitalPool.getCurrency().equals(dstCapitalPool.getCurrency()));

    boolean isEnded = false;
    List<CapitalDetail> srcDetails = capitalDetailService.getDetailsOrderByCash(srcCapitalPool);
    for (CapitalDetail srcDetail : srcDetails) {
      CapitalDetail dstDetail =
          capitalDetailService.readCapitalDetail(dstCapitalPool, srcDetail.getCashPool());
      if (dstDetail == null) {
        CreateCapitalDetailCommand crtCommand = new CreateCapitalDetailCommand();
        crtCommand.setFaCapitalPoolId(dstCapitalPool.getId());
        crtCommand.setEcaCashPoolId(srcDetail.getCashPool().getId());
        dstDetail = capitalDetailService.addFundAccountCapitalDetail(crtCommand);
      }
      Double thisAmount = 0.0;
      Double currRemain = srcDetail.getCash() - occurAmount;
      if (currRemain.doubleValue() >= 0) { // 当前明细的资金仍有剩余，则表示已经分配完
        thisAmount = occurAmount;
        isEnded = true;
      } else {
        thisAmount = srcDetail.getCash();
      }
      if (thisAmount.doubleValue() > 0) {
        occurAmount -= thisAmount;
        srcDetail.changeFloatCash(-thisAmount);
        srcDetail.changeFloatAvailableCapital(-thisAmount);
        srcDetail.changeFloatDesirableCapital(-thisAmount);
        srcDetail.changeCash(-thisAmount);
        srcDetail.changeAvailableCapital(-thisAmount);
        srcDetail.changeDesirableCapital(-thisAmount);
        capitalDetailService.save(srcDetail);

        dstDetail.changeFloatCash(thisAmount);
        dstDetail.changeFloatAvailableCapital(thisAmount);
        dstDetail.changeFloatDesirableCapital(thisAmount);
        dstDetail.changeCash(thisAmount);
        dstDetail.changeAvailableCapital(thisAmount);
        dstDetail.changeDesirableCapital(thisAmount);
        capitalDetailService.save(dstDetail);

        // 添加流水
        capitalSerialService.addCapitalSerial(FAToFACapitalSerial.class, srcFACapitalPoolId,
            dstFACapitalPoolId, srcCapitalPool.getCurrency(), thisAmount);
      }
      if (isEnded) {
        break;
      }
    }
    Assert.isTrue(occurAmount.doubleValue() == 0);
    return srcCapitalPool;
  }
}

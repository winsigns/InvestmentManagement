package com.winsigns.investment.inventoryService.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.winsigns.investment.inventoryService.capital.common.CapitalServiceManager;
import com.winsigns.investment.inventoryService.command.CreateCapitalDetailCommand;
import com.winsigns.investment.inventoryService.command.CreateCashPoolCommand;
import com.winsigns.investment.inventoryService.command.TransferBetweenECAAndECACommand;
import com.winsigns.investment.inventoryService.command.TransferBetweenFAAndECACommand;
import com.winsigns.investment.inventoryService.model.CapitalDetail;
import com.winsigns.investment.inventoryService.model.ECACashPool;
import com.winsigns.investment.inventoryService.model.ECAToECACapitalSerial;
import com.winsigns.investment.inventoryService.model.ECAToFACapitalSerial;
import com.winsigns.investment.inventoryService.model.FundAccountCapitalPool;
import com.winsigns.investment.inventoryService.repository.ECACashPoolRepository;

@Service
public class ECACashPoolService {

  @Autowired
  ECACashPoolRepository ecaCashPoolRepository;

  @Autowired
  CapitalDetailService capitalDetailService;

  @Autowired
  CapitalSerialService capitalSerialService;

  @Autowired
  CapitalServiceManager capitalServiceManager;

  /**
   * 查找特定外部资金账户下的所有资金池
   * 
   * @param externalCapitalAccountId
   * @return
   */
  public Collection<ECACashPool> findByExternalCapitalAccountId(Long externalCapitalAccountId) {

    return ecaCashPoolRepository.findByExternalCapitalAccountId(externalCapitalAccountId);
  }

  /**
   * 查找所有的资金池
   * 
   * @return
   */
  public Collection<ECACashPool> findAll() {
    return ecaCashPoolRepository.findAll();
  }

  /**
   * 查找特定的资金池
   * 
   * @param eCACashPoolId
   * @return
   */
  public ECACashPool findECACashPool(Long eCACashPoolId) {

    return ecaCashPoolRepository.findOne(eCACashPoolId);
  }

  /**
   * 添加一个资金池
   * 
   * @param createCashPoolCommand
   * @return
   */
  public ECACashPool addECACashPool(CreateCashPoolCommand createCashPoolCommand) {
    ECACashPool ecaCashPool = ecaCashPoolRepository.findByExternalCapitalAccountIdAndCurrency(
        createCashPoolCommand.getExternalCapitalAccountId(), createCashPoolCommand.getCurrency());

    if (ecaCashPool == null) {
      ecaCashPool = new ECACashPool();

      ecaCashPool.setExternalCapitalAccountId(createCashPoolCommand.getExternalCapitalAccountId());
      ecaCashPool.setCurrency(createCashPoolCommand.getCurrency());
      ecaCashPool.setUnassignedCapital(0.0);
      ecaCashPool = ecaCashPoolRepository.save(ecaCashPool);
    }
    return ecaCashPool;
  }

  /*
   * 从资金封闭圈外增加资金
   */
  @Transactional
  public ECACashPool transferTo(Long ecaCashPoolId,
      TransferBetweenFAAndECACommand transferAccountCommand) {

    ECACashPool ecaCashPool = ecaCashPoolRepository.findOne(ecaCashPoolId);

    // if (ecaCashPool == null)
    // return null;
    //
    // ecaCashPool.setUnassignedCapital(
    // ecaCashPool.getUnassignedCapital() + transferAccountCommand.getChangedCapital());
    //
    // ECACashSerial ecaCashSerial = new ECACashSerial();
    // ecaCashSerial.setEcaCashPool(ecaCashPool);
    // ecaCashSerial.setAssignedDate(new Date());
    // ecaCashSerial.setAssignedCash(Math.abs(transferAccountCommand.getChangedCapital()));
    // ecaCashSerial.operator();

    return ecaCashPoolRepository.save(ecaCashPool);
  }

  /*
   * 从资金封闭圈内转出资金
   */
  @Transactional
  public ECACashPool transferFrom(Long ecaCashPoolId,
      TransferBetweenFAAndECACommand transferAccountCommand) {

    ECACashPool ecaCashPool = ecaCashPoolRepository.findOne(ecaCashPoolId);

    if (ecaCashPool == null)
      return null;

    // ecaCashPool.setUnassignedCapital(
    // ecaCashPool.getUnassignedCapital() - transferAccountCommand.getChangedCapital());
    //
    // ECACashSerial ecaCashSerial = new ECACashSerial();
    // ecaCashSerial.setEcaCashPool(ecaCashPool);
    // ecaCashSerial.setAssignedDate(new Date());
    // ecaCashSerial.setAssignedCash(-Math.abs(transferAccountCommand.getChangedCapital()));
    // ecaCashSerial.operator();

    return ecaCashPoolRepository.save(ecaCashPool);
  }

  /**
   * 两个外部资金账户资金池之间的互转
   * 
   * @param command
   */
  public ECACashPool transferToECA(TransferBetweenECAAndECACommand command) {
    Long srcECACashPoolId = command.getSrcECACashPoolId();
    Long dstECACashPoolId = command.getDstECACashPoolId();
    Double occurAmount = Math.floor(command.getOccurAmount());
    Assert.notNull(srcECACashPoolId);
    Assert.notNull(dstECACashPoolId);

    ECACashPool srcECACashPool = ecaCashPoolRepository.findOne(srcECACashPoolId);
    Assert.notNull(srcECACashPool);
    ECACashPool dstECACashPool = ecaCashPoolRepository.findOne(dstECACashPoolId);
    Assert.notNull(dstECACashPool);
    Assert.isTrue(srcECACashPool.getCurrency().equals(dstECACashPool.getCurrency()));

    srcECACashPool.changeUnassignedCapital(-occurAmount);
    srcECACashPool = ecaCashPoolRepository.save(srcECACashPool);

    dstECACashPool.changeUnassignedCapital(occurAmount);
    dstECACashPool = ecaCashPoolRepository.save(dstECACashPool);

    capitalSerialService.addCapitalSerial(ECAToECACapitalSerial.class, srcECACashPoolId,
        dstECACashPoolId, srcECACashPool.getCurrency(), occurAmount);

    return srcECACashPool;
  }

  /**
   * 从外部资金账户分配资金到产品账户
   * 
   * @param command
   * @return
   */
  @Transactional
  public ECACashPool transferFromECAToFA(TransferBetweenFAAndECACommand command) {

    Long ecaCashPoolId = command.getEcaCashPoolId();
    Long faCapitalPoolId = command.getFaCapitalPoolId();
    Double occurAmount = Math.floor(command.getOccurAmount());
    Assert.notNull(ecaCashPoolId);
    Assert.notNull(faCapitalPoolId);

    FundAccountCapitalPool capitalPool =
        capitalServiceManager.readFundAccountCapitalPool(faCapitalPoolId);
    Assert.notNull(capitalPool);

    ECACashPool ecaCashPool = this.findECACashPool(ecaCashPoolId);
    Assert.notNull(ecaCashPool);

    Assert.isTrue(capitalPool.getCurrency().equals(ecaCashPool.getCurrency()));

    ecaCashPool.changeUnassignedCapital(-occurAmount);

    CapitalDetail capitalDetail = capitalDetailService.readCapitalDetail(capitalPool, ecaCashPool);
    if (capitalDetail == null) {
      CreateCapitalDetailCommand crtCommand = new CreateCapitalDetailCommand();
      crtCommand.setFaCapitalPoolId(faCapitalPoolId);
      crtCommand.setEcaCashPoolId(ecaCashPoolId);
      capitalDetail = capitalDetailService.addFundAccountCapitalDetail(crtCommand);
    }
    capitalDetail.changeCash(occurAmount);
    capitalDetail.changeAvailableCapital(occurAmount);
    capitalDetail.changeDesirableCapital(occurAmount);
    capitalDetailService.save(capitalDetail);

    capitalSerialService.addCapitalSerial(ECAToFACapitalSerial.class, ecaCashPoolId,
        faCapitalPoolId, capitalPool.getCurrency(), occurAmount);

    return ecaCashPool;
  }

}

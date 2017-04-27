package com.winsigns.investment.inventoryService.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.winsigns.investment.inventoryService.capital.common.CapitalServiceManager;
import com.winsigns.investment.inventoryService.command.CreateCapitalDetailCommand;
import com.winsigns.investment.inventoryService.model.CapitalDetail;
import com.winsigns.investment.inventoryService.model.ECACashPool;
import com.winsigns.investment.inventoryService.model.FundAccountCapitalPool;
import com.winsigns.investment.inventoryService.repository.CapitalDetailRepository;
import com.winsigns.investment.inventoryService.repository.CapitalSerialRepository;

@Service
public class CapitalDetailService {

  @Autowired
  CapitalDetailRepository capitalDetailRepository;

  @Autowired
  CapitalServiceManager capitalServiceManager;

  @Autowired
  ECACashPoolService ecaCashPoolService;

  @Autowired
  CapitalSerialRepository capitalSerialRepository;

  /**
   * 查询一条具体的产品资金账户明细
   * 
   * @param faCapitalDetailId
   * @return
   */
  public CapitalDetail readCapitalDetail(Long faCapitalDetailId) {
    Assert.notNull(faCapitalDetailId);
    return capitalDetailRepository.findOne(faCapitalDetailId);
  }

  /**
   * 根据产品账户资金池和外部资金账户资金池获取资金明细
   * 
   * @param capitalPool
   * @param ecaCashPool
   * @return
   */
  public CapitalDetail readCapitalDetail(FundAccountCapitalPool capitalPool,
      ECACashPool ecaCashPool) {
    return capitalDetailRepository.findByCapitalPoolAndCashPool(capitalPool, ecaCashPool);
  }

  /**
   * 创建产品资金账户明细
   * 
   * @param command
   * @return
   */
  public CapitalDetail addFundAccountCapitalDetail(CreateCapitalDetailCommand command) {

    Long faCapitalPoolId = command.getFaCapitalPoolId();
    Long ecaCapitalId = command.getEcaCashPoolId();

    Assert.notNull(faCapitalPoolId);
    FundAccountCapitalPool capitalPool =
        capitalServiceManager.readFundAccountCapitalPool(faCapitalPoolId);
    Assert.notNull(capitalPool);

    Assert.notNull(ecaCapitalId);
    ECACashPool ecaCashPool = ecaCashPoolService.findECACashPool(ecaCapitalId);
    Assert.notNull(ecaCashPool);
    Assert.isTrue(capitalPool.getCurrency().equals(ecaCashPool.getCurrency()));

    CapitalDetail capitalDetail =
        capitalDetailRepository.findByCapitalPoolAndCashPool(capitalPool, ecaCashPool);

    if (capitalDetail == null) {
      capitalDetail = new CapitalDetail();

      capitalDetail.setCapitalPool(capitalPool);
      capitalDetail.setCashPool(ecaCashPool);
      capitalDetail.setCurrency(capitalPool.getCurrency());

      capitalDetail = capitalDetailRepository.save(capitalDetail);
    }
    return capitalDetail;
  }

  /**
   * 获取特定产品账户资金池的明细
   * 
   * @param faCapitalPoolId
   * @return
   */
  public List<CapitalDetail> readDetailsByFACapitalPool(Long faCapitalPoolId) {
    Assert.notNull(faCapitalPoolId);
    FundAccountCapitalPool capitalPool =
        capitalServiceManager.readFundAccountCapitalPool(faCapitalPoolId);
    Assert.notNull(capitalPool);

    return capitalPool.getDetails();
  }

  /**
   * 保存一个资金明细
   * 
   * @param capitalDetail
   * @return
   */
  public CapitalDetail save(CapitalDetail capitalDetail) {
    return capitalDetailRepository.save(capitalDetail);
  }

  /**
   * 获取一个产品账户资金池的资金明细，并按递增排列
   * 
   * @param capitalPool
   * @return
   */
  public List<CapitalDetail> getDetailsOrderByCash(FundAccountCapitalPool capitalPool) {
    return capitalDetailRepository.findByCapitalPoolOrderByCash(capitalPool);
  }
}

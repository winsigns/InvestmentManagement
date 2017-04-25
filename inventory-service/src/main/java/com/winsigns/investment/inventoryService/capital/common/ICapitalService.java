package com.winsigns.investment.inventoryService.capital.common;

import java.util.List;

import com.winsigns.investment.framework.service.IService;
import com.winsigns.investment.inventoryService.command.CreateFundAccountCapitalPoolCommand;
import com.winsigns.investment.inventoryService.constant.CurrencyCode;
import com.winsigns.investment.inventoryService.constant.ExternalCapitalAccountType;
import com.winsigns.investment.inventoryService.exception.ResourceApplicationExcepiton;
import com.winsigns.investment.inventoryService.model.CapitalSerial;
import com.winsigns.investment.inventoryService.model.FundAccountCapitalPool;

/**
 * 资金服务的接口
 * 
 * @author yimingjin
 *
 */
public interface ICapitalService extends IService {

  /**
   * 定义资金服务的外部交易账户类型
   * 
   * @return
   */
  public ExternalCapitalAccountType getAccountType();

  /**
   * 创建产品账户资金池
   * 
   * @param command
   * @return
   */
  public FundAccountCapitalPool createFundAccountCapitalPool(
      CreateFundAccountCapitalPoolCommand command);

  /**
   * 投资服务申请资金
   * 
   * @param fundAccountId 产品账户
   * @param currency 币种
   * @param appliedCapital 申请的资金
   * @return
   * @throws ResourceApplicationExcepiton
   * 
   */
  public List<CapitalSerial> apply(Long fundAccountId, CurrencyCode currency, Double appliedCapital)
      throws ResourceApplicationExcepiton;


}

package com.winsigns.investment.tradeService.service.common;

import java.util.List;

/**
 * 交易类型的接口
 * 
 * @author yimingjin
 *
 */
public interface ITradeType {

  /**
   * 该类型的名字
   * 
   * @return
   */
  public String name();

  /**
   * 支持国际化
   * 
   * @return
   */
  public String i18n();

  /**
   * 获取支持该交易类型的投资服务类型
   * 
   * @return
   */
  public List<IRemoteInvestType> getSupportInvestTypes();
}

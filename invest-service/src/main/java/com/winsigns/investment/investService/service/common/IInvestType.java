package com.winsigns.investment.investService.service.common;

/**
 * 投资类型的通用接口
 * 
 * @author yimingjin
 *
 */
public interface IInvestType {

  /**
   * 投资类型的名字
   * 
   * @return
   */
  public String name();

  /**
   * 所属的投资类型
   * 
   * @return
   */
  public IInvestService getInvestService();

  /**
   * 支持国际化
   * 
   * @return
   */
  public String i18n();

}

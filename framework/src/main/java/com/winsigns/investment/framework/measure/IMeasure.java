package com.winsigns.investment.framework.measure;

/**
 * 指标的需要实现的接口
 * 
 * @author Created by colin on 2017/3/2.
 * @since 0.0.2
 */
public interface IMeasure {

  /**
   * 定义指标的简称
   * 
   * @return 指标的简称
   */
  public String getName();

  /**
   * 定义指标的全称
   * 
   * @return 指标的全称
   */
  public String getFullName();
}

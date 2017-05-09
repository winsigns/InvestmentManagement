package com.winsigns.investment.framework.manager;

public interface IMember {

  /**
   * 获取成员的名字
   * 
   * @return
   */
  public String getName();

  /**
   * 将成员注册到manager中
   */
  public void register();
}

package com.winsigns.investment.framework.service;

/**
 * 受服务管理管理的服务接口
 * 
 * @author yimingjin
 *
 * @since 0.0.4
 */
public interface IService {

  /**
   * 服务名称
   * 
   * @return
   */
  public String getName();

  /**
   * 
   * @return 服务简称
   */
  public String getSimpleName();

}

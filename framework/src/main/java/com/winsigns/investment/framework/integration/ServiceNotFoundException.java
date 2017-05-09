package com.winsigns.investment.framework.integration;

import com.winsigns.investment.framework.exception.CustomArgumentsException;

/**
 * 服务未找到
 * 
 * @author yimingjin
 * @since 0.0.4
 */
public class ServiceNotFoundException extends CustomArgumentsException {

  /**
   * 
   */
  private static final long serialVersionUID = -3314817683941243219L;

  public ServiceNotFoundException(Object[] args) {
    super(args);
  }

}

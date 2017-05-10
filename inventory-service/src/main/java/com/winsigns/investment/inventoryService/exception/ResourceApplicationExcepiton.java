package com.winsigns.investment.inventoryService.exception;

import com.winsigns.investment.framework.exception.CommonException;

/**
 * 所有资源申请相关的错误基类
 * 
 * @author yimingjin
 *
 */
public class ResourceApplicationExcepiton extends CommonException {
  /**
   * 
   */
  private static final long serialVersionUID = -6038447188657669743L;

  @Override
  protected String getPrefix() {
    return super.getPrefix() + "ResourceApplicationExcepiton.";
  }
}

package com.winsigns.investment.framework.exception;

import com.winsigns.investment.framework.i18n.i18nHelper;

public class CustomArgumentsException extends CommonException implements ICustomArguments {

  private final Object[] args;
  /**
   * 
   */
  private static final long serialVersionUID = 2701458721665587726L;

  public CustomArgumentsException(Object[] args) {
    this.args = args;
  }

  /**
   * 获取错误信息
   */
  @Override
  public String getMessage() {
    return i18nHelper.i18n(getCode(), getArguments());
  }

  @Override
  public Object[] getArguments() {
    return this.args;
  }

  @Override
  protected String getPrefix() {
    return "CustomArgumentsException.";
  }

}

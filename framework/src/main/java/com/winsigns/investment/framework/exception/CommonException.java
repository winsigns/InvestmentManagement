package com.winsigns.investment.framework.exception;

import lombok.Getter;

/**
 * 通用的异常
 * 
 * @author yimingjin
 * @since 0.0.4
 */
public class CommonException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = 7438627252255549940L;

  @Getter
  private final IError error;

  public CommonException(IError error) {
    super(error.message());

    this.error = error;
  }

  /**
   * 获取错误码
   * 
   * @return
   */
  public String getCode() {
    return error.code();
  }
}

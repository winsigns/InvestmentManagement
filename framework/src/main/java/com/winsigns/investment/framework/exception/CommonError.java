package com.winsigns.investment.framework.exception;

import lombok.AllArgsConstructor;

/**
 * 通用的错误
 * 
 * @author yimingjin
 * @since 0.0.4
 *
 */
@AllArgsConstructor
public class CommonError implements IError {

  private String code;

  private String message;

  @Override
  public String code() {
    return this.code;
  }

  @Override
  public String message() {
    return this.message;
  }

}

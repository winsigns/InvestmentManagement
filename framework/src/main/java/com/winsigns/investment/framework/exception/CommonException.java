package com.winsigns.investment.framework.exception;

import java.util.ArrayList;
import java.util.List;

import com.winsigns.investment.framework.i18n.i18nHelper;

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

  protected List<CommonError> errors = new ArrayList<CommonError>();

  public CommonException() {

  }

  public CommonException(CommonException e) {
    CommonError error = new CommonError(e.getCode(), e.getFullMessage());
    this.errors.add(error);
  }

  public CommonException(String e) {
    CommonError error = CommonError.fromString(e);
    this.errors.add(error);
  }

  protected String getPrefix() {
    return "CommonException.";
  }

  /**
   * 获取错误码
   * 
   * @return
   */
  public String getCode() {
    return getPrefix() + this.getClass().getSimpleName();
  }

  /**
   * 获取错误信息
   */
  @Override
  public String getMessage() {
    return i18nHelper.i18n(getCode());
  }

  public void addError(CommonError error) {
    this.errors.add(error);
  }

  public String getFullMessage() {
    String str = new String();
    for (CommonError error : errors) {
      str = error.getMessage() + "-" + str;
    }
    str = getMessage() + "-" + str;
    return str.substring(0, str.length() - 1);
  }
}

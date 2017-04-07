package com.winsigns.investment.investService.constant;

import com.winsigns.investment.framework.i18n.i18nHelper;

/**
 * 指令的错误
 * 
 * @author yimingjin
 *
 */
public enum InstructionMessageCode {

  PORTFOLIO_NOT_NULL,

  PORTFOLIO_NOT_MATCHED_INVESTMANAGER;
  /**
   * 国际化
   * 
   * @return
   */
  public String i18n() {
    return i18nHelper.i18n(this);
  }
}

package com.winsigns.investment.investService.constant;

import com.winsigns.investment.framework.i18n.i18nHelper;

public enum InstructionMessageType {

  ERROR,

  WARNING;
  /**
   * 国际化
   * 
   * @return
   */
  public String i18n() {
    return i18nHelper.i18n(this);
  }
}

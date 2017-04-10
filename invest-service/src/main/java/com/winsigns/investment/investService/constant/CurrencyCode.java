package com.winsigns.investment.investService.constant;

import com.winsigns.investment.framework.i18n.i18nHelper;

/**
 * 
 * @author yimingjin
 *
 */
public enum CurrencyCode {
  // 人民币
  CNY,

  // 港币
  HKD,

  // 美元
  USD;

  /**
   * 国际化
   * 
   * @return
   */
  public String i18n() {
    return i18nHelper.i18n(this);
  }
}

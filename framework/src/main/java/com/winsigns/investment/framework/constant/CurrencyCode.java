package com.winsigns.investment.framework.constant;

import com.winsigns.investment.framework.i18n.i18nHelper;

/**
 * 将币种放入框架，并提供国际化
 * 
 * @author yimingjin
 * @since 0.0.4
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

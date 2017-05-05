package com.winsigns.investment.framework.constant;

import java.util.Currency;
import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;

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
    Locale locale = LocaleContextHolder.getLocale();
    if (locale.equals(Locale.CHINESE)) { // 默认是简体中文
      locale = Locale.CHINA;
    }
    return Currency.getInstance(this.name()).getDisplayName(locale);
  }
}

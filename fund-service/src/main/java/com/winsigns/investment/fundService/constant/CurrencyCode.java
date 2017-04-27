package com.winsigns.investment.fundService.constant;

import com.winsigns.investment.framework.i18n.i18nHelper;

/**
 * Created by colin on 2017/2/23.
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


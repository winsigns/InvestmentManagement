package com.winsigns.investment.tradeService.constant;

import com.winsigns.investment.framework.i18n.i18nHelper;

/**
 * 委托的错误
 * 
 * @author yimingjin
 *
 */
public enum EntrustMessageCode {
  // 交易服务不能为空
  TRADE_SERVICE_CANNOT_NULL,

  // 交易类型不能为空
  TRADE_TYPE_CANNOT_NULL,;
  /**
   * 国际化
   * 
   * @return
   */
  public String i18n() {
    return i18nHelper.i18n(this);
  }
}

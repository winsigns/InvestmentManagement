package com.winsigns.investment.inventoryService.constant;

import com.winsigns.investment.framework.i18n.i18nHelper;

public enum ExternalTradeAccountType {

  // 沪市A股账户
  SSE_A_STOCK_ACCOUNT,

  // 沪市B股账户
  SSE_B_STOCK_ACCOUNT,

  // 深市A股账户
  SZSE_A_STOCK_ACCOUNT,

  // 深市B股账户
  SZSE_B_STOCK_ACCOUNT,

  // 大商所交易编码
  DCE_TRADING_CODE,

  // 上期所交易编码
  SHFE_TRADING_CODE,

  // 郑商所交易编码
  CZCE_TRADING_CODE,

  // 中金所交易编码
  CFFEX_TRADING_CODE;

  public String i18n() {
    return i18nHelper.i18n(this);
  }
}

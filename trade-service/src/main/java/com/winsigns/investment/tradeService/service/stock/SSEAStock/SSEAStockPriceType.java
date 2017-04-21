package com.winsigns.investment.tradeService.service.stock.SSEAStock;

import com.winsigns.investment.framework.i18n.i18nHelper;
import com.winsigns.investment.tradeService.service.common.IPriceType;

public enum SSEAStockPriceType implements IPriceType {

  // 限价委托
  LIMIT_ENTRUST,

  // 最优五档即时成交剩余撤销市价委托
  MARKET_PRICE_RECALL,

  // 最优五档即时成交剩余转限价市价委托
  MARKET_PRICE_LIMIT;

  public String i18n() {
    return i18nHelper.i18n(this);
  }
}

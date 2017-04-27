package com.winsigns.investment.tradeService.service.stock;

import static java.util.Arrays.asList;

import java.util.HashMap;
import java.util.List;

import com.winsigns.investment.framework.i18n.i18nHelper;
import com.winsigns.investment.tradeService.service.common.IRemoteInvestType;
import com.winsigns.investment.tradeService.service.common.ITradeType;

public enum StockTradeType implements ITradeType {

  BUY,

  SELL;

  public String i18n() {
    return i18nHelper.i18n(this);
  }

  // 支持的操作
  private static HashMap<StockTradeType, List<IRemoteInvestType>> supportInvestTypes =
      new HashMap<StockTradeType, List<IRemoteInvestType>>();
  static {
    supportInvestTypes.put(BUY, asList(StockInvestType.BUY));
    supportInvestTypes.put(SELL, asList(StockInvestType.SELL));
  }

  @Override
  public List<IRemoteInvestType> getSupportInvestTypes() {
    return supportInvestTypes.get(this);
  }

}

package com.winsigns.investment.investService.service.stockInvestService;

import com.winsigns.investment.framework.i18n.i18nHelper;
import com.winsigns.investment.investService.service.common.IInvestService;
import com.winsigns.investment.investService.service.common.IInvestType;
import com.winsigns.investment.investService.service.common.InvestServiceManager;

/**
 * 现货投资类型
 * 
 * @author yimingjin
 *
 */
public enum StockInvestType implements IInvestType {

  // 买入
  BUY,

  // 卖出
  SELL;

  @Override
  public String i18n() {
    return i18nHelper.i18n(this);
  }

  @Override
  public IInvestService getInvestService() {
    return InvestServiceManager.getInvestServiceManager()
        .getService(StockInvestService.class.getSimpleName());
  }
}

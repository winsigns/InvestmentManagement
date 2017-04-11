package com.winsigns.investment.investService.service.futureInvestService;

import com.winsigns.investment.framework.i18n.i18nHelper;
import com.winsigns.investment.investService.service.common.IInvestService;
import com.winsigns.investment.investService.service.common.IInvestType;
import com.winsigns.investment.investService.service.common.InvestServiceManager;

/**
 * 期货投资类型
 * 
 * @author yimingjin
 *
 */
public enum FutureInvestType implements IInvestType {

  BUY,

  SELL;

  @Override
  public String i18n() {
    return i18nHelper.i18n(this);
  }

  @Override
  public IInvestService getInvestService() {
    return InvestServiceManager.getInstance().getService(FutureInvestService.class.getName());
  }

}

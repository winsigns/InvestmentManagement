package com.winsigns.investment.investService.service.common;

import com.winsigns.investment.framework.i18n.i18nHelper;

public enum DefaultInvestType implements IInvestType {
  DEFAULT;

  @Override
  public IInvestService getInvestService() {

    return InvestServiceManager.getInvestServiceManager()
        .getService(DefaultInvestService.class.getName());
  }

  @Override
  public String i18n() {
    return i18nHelper.i18n(this);
  }

}

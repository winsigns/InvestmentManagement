package com.winsigns.investment.investService.service.common;

import com.winsigns.investment.framework.i18n.i18nHelper;

/**
 * 该类的作用：
 * <p>
 * 在查询投资类型的时候，不再需要判断是否为null，简化代码
 * 
 * @author yimingjin
 *
 */
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

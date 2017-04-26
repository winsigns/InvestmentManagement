package com.winsigns.investment.investService.service.common;

import com.winsigns.investment.framework.i18n.i18nHelper;
import com.winsigns.investment.investService.model.Instruction;

public class DefaultInvestService implements IInvestService {


  @Override
  public IInvestType[] getInvestType() {
    return DefaultInvestType.values();
  }

  @Override
  public IInvestType getInvestType(String name) {
    return DefaultInvestType.DEFAULT;
  }

  @Override
  public String getName() {
    return this.getClass().getSimpleName();
  }

  @Override
  public String getSimpleName() {
    return i18nHelper.i18n(getName());
  }

  @Override
  public boolean commitInstruction(Instruction instruction) {
    return false;
  }
}

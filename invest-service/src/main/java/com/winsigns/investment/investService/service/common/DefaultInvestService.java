package com.winsigns.investment.investService.service.common;

import com.winsigns.investment.framework.i18n.i18nHelper;
import com.winsigns.investment.investService.exception.InvestCommitFailedExcepiton;
import com.winsigns.investment.investService.model.Instruction;

/**
 * 该类的作用：
 * <p>
 * 在查询服务的时候，不再需要判断是否为null，简化代码
 * 
 * @author yimingjin
 *
 */
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
  public void commitInstruction(Instruction instruction) throws InvestCommitFailedExcepiton {}
}

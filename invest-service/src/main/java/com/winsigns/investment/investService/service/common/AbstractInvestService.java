package com.winsigns.investment.investService.service.common;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.winsigns.investment.framework.i18n.i18nHelper;
import com.winsigns.investment.investService.model.Instruction;

/**
 * 实现一种简单的投资服务的抽象类
 * 
 * @author yimingjin
 *
 */
@Service
public abstract class AbstractInvestService implements IInvestService {

  @Override
  public String getName() {
    return this.getClass().getSimpleName();
  }

  @Override
  public String getSimpleName() {
    return i18nHelper.i18n(getName());
  }

  @PostConstruct
  private void register() {
    InvestServiceManager.getInstance().register(this);
  }

  @Override
  public void commitInstruction(Instruction instruction) {

  }
}

package com.winsigns.investment.tradeService.service.common;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.winsigns.investment.framework.i18n.i18nHelper;


/**
 * 实现一种简单的交易服务的抽象类
 * 
 * @author yimingjin
 *
 */
@Service
public abstract class AbstractTradeService implements ITradeService {

  @PostConstruct
  private void register() {
    TradeServiceManager.getInstance().register(this);
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
  public ITradeType getTradeType(String name) {
    for (ITradeType type : this.getTradeType()) {
      if (type.name().equals(name)) {
        return type;
      }
    }
    return null;
  }

}

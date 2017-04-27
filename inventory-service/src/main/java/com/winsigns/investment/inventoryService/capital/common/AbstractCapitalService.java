package com.winsigns.investment.inventoryService.capital.common;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winsigns.investment.framework.i18n.i18nHelper;

@Service
public abstract class AbstractCapitalService implements ICapitalService {

  @Autowired
  CapitalServiceManager manager;

  @PostConstruct
  public void register() {
    manager.register(this);
  }

  @Override
  public String getName() {
    return this.getClass().getSimpleName();
  }

  @Override
  public String getSimpleName() {
    return i18nHelper.i18n(getName());
  }

}

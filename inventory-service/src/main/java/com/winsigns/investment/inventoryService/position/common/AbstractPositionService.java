package com.winsigns.investment.inventoryService.position.common;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winsigns.investment.framework.i18n.i18nHelper;

@Service
public abstract class AbstractPositionService implements IPositionService {

  @Autowired
  PositionServiceManager manager;

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

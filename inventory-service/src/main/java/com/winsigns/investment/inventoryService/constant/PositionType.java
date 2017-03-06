package com.winsigns.investment.inventoryService.constant;

import com.winsigns.investment.framework.i18n.i18nHelper;

public enum PositionType {

  STOCK;

  public String i18n() {
    return i18nHelper.i18n(this);
  }
}

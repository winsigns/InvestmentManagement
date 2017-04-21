package com.winsigns.investment.inventoryService.resource;

import com.winsigns.investment.framework.measure.resource.MeasureHostResource;
import com.winsigns.investment.inventoryService.model.ECACashPool;

public class ECACashPoolResource extends MeasureHostResource<ECACashPool> {

  final String currencyLabel;

  public ECACashPoolResource(ECACashPool ecaCashPool) {
    super(ecaCashPool);
    this.currencyLabel = ecaCashPool.getCurrency().i18n();
  }

}

package com.winsigns.investment.inventoryService.resource;

import org.springframework.hateoas.core.Relation;

import com.winsigns.investment.framework.measure.resource.MeasureHostResource;
import com.winsigns.investment.inventoryService.model.ECACashPool;

@Relation(value = "eca-cash-pool", collectionRelation = "eca-cash-pools")
public class ECACashPoolResource extends MeasureHostResource<ECACashPool> {

  final String currencyLabel;

  public ECACashPoolResource(ECACashPool ecaCashPool) {
    super(ecaCashPool);
    this.currencyLabel = ecaCashPool.getCurrency().i18n();
  }

}

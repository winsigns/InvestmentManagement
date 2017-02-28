package com.winsigns.investment.tradeService.resource;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.winsigns.investment.tradeService.service.TradeServiceBase;

@Relation(value = "trade-service", collectionRelation = "trade-services")
public class TradeServiceResource extends ResourceSupport {

  private final String name;

  public TradeServiceResource(TradeServiceBase tradeServiceBase) {
    this.name = tradeServiceBase.getName();
  }

  public String getName() {
    return name;
  }
}

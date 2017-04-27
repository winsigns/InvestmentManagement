package com.winsigns.investment.tradeService.resource;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.winsigns.investment.tradeService.service.common.ITradeService;

@Relation(value = "trade-service", collectionRelation = "trade-services")
public class TradeServiceResource extends ResourceSupport {

  private final String name;

  public TradeServiceResource(ITradeService service) {
    this.name = service.getName();
  }

  public String getName() {
    return name;
  }
}

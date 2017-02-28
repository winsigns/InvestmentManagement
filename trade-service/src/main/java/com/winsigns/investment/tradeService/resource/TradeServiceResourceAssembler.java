package com.winsigns.investment.tradeService.resource;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.winsigns.investment.tradeService.controller.TradeController;
import com.winsigns.investment.tradeService.service.TradeServiceBase;

public class TradeServiceResourceAssembler
    extends ResourceAssemblerSupport<TradeServiceBase, TradeServiceResource> {

  public TradeServiceResourceAssembler() {
    super(TradeController.class, TradeServiceResource.class);
  }

  @Override
  public TradeServiceResource toResource(TradeServiceBase tradeServiceBase) {
    return createResourceWithId(tradeServiceBase.getName(), tradeServiceBase);
  }

  @Override
  protected TradeServiceResource instantiateResource(TradeServiceBase entity) {
    return new TradeServiceResource(entity);
  }
}

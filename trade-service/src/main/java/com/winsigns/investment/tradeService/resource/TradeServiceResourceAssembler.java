package com.winsigns.investment.tradeService.resource;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.winsigns.investment.tradeService.controller.TradeController;
import com.winsigns.investment.tradeService.service.common.ITradeService;

public class TradeServiceResourceAssembler
    extends ResourceAssemblerSupport<ITradeService, TradeServiceResource> {

  public TradeServiceResourceAssembler() {
    super(TradeController.class, TradeServiceResource.class);
  }

  @Override
  public TradeServiceResource toResource(ITradeService service) {
    return createResourceWithId(service.getName(), service);
  }

  @Override
  protected TradeServiceResource instantiateResource(ITradeService entity) {
    return new TradeServiceResource(entity);
  }
}

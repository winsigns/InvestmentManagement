package com.winsigns.investment.inventoryService.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.winsigns.investment.inventoryService.controller.FundAccountCapitalPoolController;
import com.winsigns.investment.inventoryService.model.FundAccountCapitalPool;

public class FundAccountCapitalPoolResourceAssembler
    extends ResourceAssemblerSupport<FundAccountCapitalPool, FundAccountCapitalPoolResource> {

  public FundAccountCapitalPoolResourceAssembler() {
    super(FundAccountCapitalPoolController.class, FundAccountCapitalPoolResource.class);
  }

  @Override
  public FundAccountCapitalPoolResource toResource(FundAccountCapitalPool capitalPool) {
    FundAccountCapitalPoolResource capitalPoolResource =
        createResourceWithId(capitalPool.getId(), capitalPool);

    capitalPoolResource.add(linkTo(methodOn(FundAccountCapitalPoolController.class)
        .transferToECACashPool(capitalPool.getId(), null)).withRel("to-eca"));
    capitalPoolResource.add(linkTo(methodOn(FundAccountCapitalPoolController.class)
        .transferToFACapitalPool(capitalPool.getId(), null)).withRel("to-fa"));
    return capitalPoolResource;
  }

  @Override
  protected FundAccountCapitalPoolResource instantiateResource(FundAccountCapitalPool entity) {
    return new FundAccountCapitalPoolResource(entity);
  }
}

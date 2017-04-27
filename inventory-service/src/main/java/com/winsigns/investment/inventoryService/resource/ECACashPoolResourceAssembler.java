package com.winsigns.investment.inventoryService.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.winsigns.investment.framework.measure.resource.MeasureHostResourceAssembler;
import com.winsigns.investment.inventoryService.controller.ECACashPoolController;
import com.winsigns.investment.inventoryService.model.ECACashPool;

public class ECACashPoolResourceAssembler
    extends MeasureHostResourceAssembler<ECACashPool, ECACashPoolResource> {

  public ECACashPoolResourceAssembler() {
    super(ECACashPoolController.class, ECACashPoolResource.class);
  }

  @Override
  public ECACashPoolResource toResource(ECACashPool ecaCashPool) {

    ECACashPoolResource ecaCashPoolResource =
        createResourceWithId(ecaCashPool.getId(), ecaCashPool);


    ecaCashPoolResource.add(linkTo(
        methodOn(ECACashPoolController.class).transferToFundAccount(ecaCashPool.getId(), null))
            .withRel("to-fa"));
    ecaCashPoolResource
        .add(linkTo(methodOn(ECACashPoolController.class).transferToECA(ecaCashPool.getId(), null))
            .withRel("to-eca"));

    return ecaCashPoolResource;
  }

  @Override
  protected ECACashPoolResource instantiateResource(ECACashPool entity) {
    return new ECACashPoolResource(entity);
  }
}

package com.winsigns.investment.inventoryService.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.winsigns.investment.inventoryService.command.AllotAccountCommand;
import com.winsigns.investment.inventoryService.command.TransferAccountCommand;
import com.winsigns.investment.inventoryService.controller.ECACashPoolController;
import com.winsigns.investment.inventoryService.model.ECACashPool;

public class ECACashPoolResourceAssembler
    extends ResourceAssemblerSupport<ECACashPool, ECACashPoolResource> {

  public ECACashPoolResourceAssembler() {
    super(ECACashPoolController.class, ECACashPoolResource.class);
  }

  @Override
  public ECACashPoolResource toResource(ECACashPool ecaCashPool) {

    ECACashPoolResource ecaCashPoolResource =
        createResourceWithId(ecaCashPool.getId(), ecaCashPool);

    ecaCashPoolResource.add(linkTo(methodOn(ECACashPoolController.class)
        .transferTo(ecaCashPool.getId(), new TransferAccountCommand())).withRel("transfer-to"));
    ecaCashPoolResource.add(linkTo(methodOn(ECACashPoolController.class)
        .transferFrom(ecaCashPool.getId(), new TransferAccountCommand())).withRel("transfer-from"));

    ecaCashPoolResource.add(linkTo(methodOn(ECACashPoolController.class)
        .allotIn(ecaCashPool.getId(), new AllotAccountCommand())).withRel("allot-in"));
    ecaCashPoolResource.add(linkTo(methodOn(ECACashPoolController.class)
        .allotOut(ecaCashPool.getId(), new AllotAccountCommand())).withRel("allot-out"));

    return ecaCashPoolResource;
  }

  @Override
  protected ECACashPoolResource instantiateResource(ECACashPool entity) {
    return new ECACashPoolResource(entity);
  }
}

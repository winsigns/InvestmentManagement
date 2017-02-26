package com.winsigns.investment.inventoryService.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.winsigns.investment.inventoryService.command.AllotAccountCommand;
import com.winsigns.investment.inventoryService.command.TransferAccountCommand;
import com.winsigns.investment.inventoryService.controller.ECACashPoolController;
import com.winsigns.investment.inventoryService.model.ECACashPool;

public class ECACashPoolResourceAssembler extends ResourceAssemblerSupport<ECACashPool, ECACashPoolResource> {

    public ECACashPoolResourceAssembler() {
        super(ECACashPoolController.class, ECACashPoolResource.class);
    }

    @Override
    public ECACashPoolResource toResource(ECACashPool ecaCashPool) {

        ECACashPoolResource ecaCashPoolResource = createResourceWithId(ecaCashPool.getId(), ecaCashPool);

        ecaCashPoolResource.add(linkTo(
                methodOn(ECACashPoolController.class).transferTo(ecaCashPool.getId(), new TransferAccountCommand()))
                        .withRel("transferTo"));
        ecaCashPoolResource.add(linkTo(
                methodOn(ECACashPoolController.class).transferFrom(ecaCashPool.getId(), new TransferAccountCommand()))
                        .withRel("transferFrom"));

        ecaCashPoolResource.add(
                linkTo(methodOn(ECACashPoolController.class).allotIn(ecaCashPool.getId(), new AllotAccountCommand()))
                        .withRel("allotIn"));
        ecaCashPoolResource.add(
                linkTo(methodOn(ECACashPoolController.class).allotOut(ecaCashPool.getId(), new AllotAccountCommand()))
                        .withRel("allotOut"));

        return ecaCashPoolResource;
    }

    @Override
    protected ECACashPoolResource instantiateResource(ECACashPool entity) {
        return new ECACashPoolResource(entity);
    }
}

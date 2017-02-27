package com.winsigns.investment.tradeService.resource;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.winsigns.investment.tradeService.controller.EntrustController;
import com.winsigns.investment.tradeService.model.Entrust;

public class EntrustResourceAssembler extends ResourceAssemblerSupport<Entrust, EntrustResource> {

    public EntrustResourceAssembler() {
        super(EntrustController.class, EntrustResource.class);
    }

    @Override
    public EntrustResource toResource(Entrust entrust) {
        return createResourceWithId(entrust.getId(), entrust);
    }

    @Override
    protected EntrustResource instantiateResource(Entrust entity) {
        return new EntrustResource(entity);
    }
}

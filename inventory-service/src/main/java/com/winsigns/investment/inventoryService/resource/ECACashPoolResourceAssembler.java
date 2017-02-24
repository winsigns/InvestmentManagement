package com.winsigns.investment.inventoryService.resource;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.winsigns.investment.inventoryService.controller.ECACashPoolController;
import com.winsigns.investment.inventoryService.model.ECACashPool;

public class ECACashPoolResourceAssembler extends ResourceAssemblerSupport<ECACashPool, ECACashPoolResource> {

    public ECACashPoolResourceAssembler() {
        super(ECACashPoolController.class, ECACashPoolResource.class);
    }

    @Override
    public ECACashPoolResource toResource(ECACashPool eCACashPool) {
        return createResourceWithId(eCACashPool.getId(), eCACashPool, eCACashPool.getFundId(),
                eCACashPool.getExternalCapitalAccountId());
    }

    @Override
    protected ECACashPoolResource instantiateResource(ECACashPool entity) {
        return new ECACashPoolResource(entity);
    }
}

package com.winsigns.investment.inventoryService.resource;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.winsigns.investment.inventoryService.controller.ExternalCapitalAccountCapitalController;
import com.winsigns.investment.inventoryService.model.ExternalCapitalAccountCapital;

public class ExternalCapitalAccountCapitalResourceAssembler
		extends ResourceAssemblerSupport<ExternalCapitalAccountCapital, ExternalCapitalAccountCapitalResource> {

	public ExternalCapitalAccountCapitalResourceAssembler() {
		super(ExternalCapitalAccountCapitalController.class, ExternalCapitalAccountCapitalResource.class);
	}

	@Override
	public ExternalCapitalAccountCapitalResource toResource(
			ExternalCapitalAccountCapital externalCapitalAccountCapital) {
		return createResourceWithId(externalCapitalAccountCapital.getId(), externalCapitalAccountCapital);
	}

	@Override
	protected ExternalCapitalAccountCapitalResource instantiateResource(ExternalCapitalAccountCapital entity) {
		return new ExternalCapitalAccountCapitalResource(entity);
	}
}

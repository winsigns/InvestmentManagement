package com.winsigns.investment.inventoryService.resource;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.winsigns.investment.inventoryService.controller.ExternalCapitalAccountController;
import com.winsigns.investment.inventoryService.model.ExternalCapitalAccount;

public class ExternalCapitalAccountResourceAssembler
		extends ResourceAssemblerSupport<ExternalCapitalAccount, ExternalCapitalAccountResource> {

	public ExternalCapitalAccountResourceAssembler() {
		super(ExternalCapitalAccountController.class, ExternalCapitalAccountResource.class);
	}

	@Override
	public ExternalCapitalAccountResource toResource(ExternalCapitalAccount externalCapitalAccount) {
		return createResourceWithId(externalCapitalAccount.getId(), externalCapitalAccount,
				externalCapitalAccount.getFundId());
	}

	@Override
	protected ExternalCapitalAccountResource instantiateResource(ExternalCapitalAccount entity) {
		return new ExternalCapitalAccountResource(entity);
	}
}

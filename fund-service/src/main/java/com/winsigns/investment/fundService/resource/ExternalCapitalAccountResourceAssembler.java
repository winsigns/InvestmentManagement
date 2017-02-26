package com.winsigns.investment.fundService.resource;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.winsigns.investment.fundService.controller.ExternalCapitalAccountController;
import com.winsigns.investment.fundService.model.ExternalCapitalAccount;

public class ExternalCapitalAccountResourceAssembler
		extends ResourceAssemblerSupport<ExternalCapitalAccount, ExternalCapitalAccountResource> {

	public ExternalCapitalAccountResourceAssembler() {
		super(ExternalCapitalAccountController.class, ExternalCapitalAccountResource.class);
	}

	@Override
	public ExternalCapitalAccountResource toResource(ExternalCapitalAccount externalCapitalAccount) {
		return createResourceWithId(externalCapitalAccount.getId(), externalCapitalAccount,
				externalCapitalAccount.getFund().getId(), externalCapitalAccount.getId());
	}

	@Override
	protected ExternalCapitalAccountResource instantiateResource(ExternalCapitalAccount entity) {
		return new ExternalCapitalAccountResource(entity);
	}
}

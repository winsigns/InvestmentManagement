package com.winsigns.investment.fundService.resource;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.winsigns.investment.fundService.controller.ExternalTradeAccountController;
import com.winsigns.investment.fundService.model.ExternalTradeAccount;

public class ExternalTradeAccountResourceAssembler
		extends ResourceAssemblerSupport<ExternalTradeAccount, ExternalTradeAccountResource> {

	public ExternalTradeAccountResourceAssembler() {
		super(ExternalTradeAccountController.class, ExternalTradeAccountResource.class);
	}

	@Override
	public ExternalTradeAccountResource toResource(ExternalTradeAccount externalTradeAccount) {
		return createResourceWithId(externalTradeAccount.getId(), externalTradeAccount,
				externalTradeAccount.getExternalCapitalAccount().getFund().getId(),
				externalTradeAccount.getExternalCapitalAccount().getId());
	}

	@Override
	protected ExternalTradeAccountResource instantiateResource(ExternalTradeAccount entity) {
		return new ExternalTradeAccountResource(entity);
	}
}

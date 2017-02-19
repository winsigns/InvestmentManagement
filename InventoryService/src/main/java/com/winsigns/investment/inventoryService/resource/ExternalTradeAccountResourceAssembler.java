package com.winsigns.investment.inventoryService.resource;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.winsigns.investment.inventoryService.controller.ExternalTradeAccountController;
import com.winsigns.investment.inventoryService.model.ExternalTradeAccount;

public class ExternalTradeAccountResourceAssembler
		extends ResourceAssemblerSupport<ExternalTradeAccount, ExternalTradeAccountResource> {

	public ExternalTradeAccountResourceAssembler() {
		super(ExternalTradeAccountController.class, ExternalTradeAccountResource.class);
	}

	@Override
	public ExternalTradeAccountResource toResource(ExternalTradeAccount externalTradeAccount) {
		return createResourceWithId(externalTradeAccount.getId(), externalTradeAccount,
				externalTradeAccount.getExternalCapitalAccount().getId(),
				externalTradeAccount.getExternalCapitalAccount().getFundId());
	}

	@Override
	protected ExternalTradeAccountResource instantiateResource(ExternalTradeAccount entity) {
		return new ExternalTradeAccountResource(entity);
	}
}

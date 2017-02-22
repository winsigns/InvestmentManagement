package com.winsigns.investment.fundService.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.winsigns.investment.fundService.controller.ExternalTradeAccountController;
import com.winsigns.investment.fundService.controller.FundController;
import com.winsigns.investment.fundService.model.ExternalCapitalAccount;
import com.winsigns.investment.fundService.model.ExternalCapitalAccountType;
import com.winsigns.investment.fundService.model.ExternalOpenOrganization;

public class ExternalCapitalAccountResource extends Resource<ExternalCapitalAccount> {

	private final ExternalCapitalAccountType externalCapitalAccountType;

	private final String externalCapitalAccount;

	private final ExternalOpenOrganization externalOpenOrganization;

	@JsonUnwrapped
	private final Resources<ExternalTradeAccountResource> externalTradeAccountResources;

	public ExternalCapitalAccountResource(ExternalCapitalAccount externalCapitalAccount) {
		super(externalCapitalAccount);

		this.externalCapitalAccountType = externalCapitalAccount.getExternalCapitalAccountType();
		this.externalCapitalAccount = externalCapitalAccount.getExternalCapitalAccount();
		this.externalOpenOrganization = externalCapitalAccount.getExternalOpenOrganization();

		this.externalTradeAccountResources = new Resources<ExternalTradeAccountResource>(
				new ExternalTradeAccountResourceAssembler()
						.toResources(externalCapitalAccount.getExternalTradeAccounts()));

		add(linkTo(methodOn(ExternalTradeAccountController.class)
				.readExternalTradeAccounts(externalCapitalAccount.getFund().getId(), externalCapitalAccount.getId()))
						.withRel("externalTradeAccounts"));
		add(linkTo(methodOn(FundController.class).readFund(externalCapitalAccount.getFund().getId())).withRel("fund"));
	}

	public ExternalCapitalAccountType getExternalCapitalAccountType() {
		return externalCapitalAccountType;
	}

	public String getExternalCapitalAccount() {
		return externalCapitalAccount;
	}

	public ExternalOpenOrganization getExternalOpenOrganization() {
		return externalOpenOrganization;
	}

}

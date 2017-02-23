package com.winsigns.investment.fundService.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.winsigns.investment.fundService.constant.ExternalCapitalAccountType;
import com.winsigns.investment.fundService.constant.ExternalOpenOrganization;
import com.winsigns.investment.fundService.controller.ExternalTradeAccountController;
import com.winsigns.investment.fundService.controller.FundController;
import com.winsigns.investment.fundService.hal.HALResponse;
import com.winsigns.investment.fundService.model.ExternalCapitalAccount;

public class ExternalCapitalAccountResource extends HALResponse<ExternalCapitalAccount> {

	private final ExternalCapitalAccountType externalCapitalAccountType;

	private final String externalCapitalAccount;

	private final ExternalOpenOrganization externalOpenOrganization;

	public ExternalCapitalAccountResource(ExternalCapitalAccount externalCapitalAccount) {
		super(externalCapitalAccount);

		this.externalCapitalAccountType = externalCapitalAccount.getExternalCapitalAccountType();
		this.externalCapitalAccount = externalCapitalAccount.getExternalCapitalAccount();
		this.externalOpenOrganization = externalCapitalAccount.getExternalOpenOrganization();

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

package com.winsigns.investment.fundService.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;

import com.winsigns.investment.fundService.constant.ExternalTradeAccountType;
import com.winsigns.investment.fundService.controller.ExternalCapitalAccountController;
import com.winsigns.investment.fundService.model.ExternalTradeAccount;

public class ExternalTradeAccountResource extends Resource<ExternalTradeAccount> {

	private final ExternalTradeAccountType externalTradeAccountType;

	private final String externalTradeAccount;


	public ExternalTradeAccountResource(ExternalTradeAccount externalTradeAccount) {
		super(externalTradeAccount);
		this.externalTradeAccount = externalTradeAccount.getExternalTradeAccount();
		this.externalTradeAccountType = externalTradeAccount.getExternalTradeAccountType();

		Long externalCapitalAccountId = externalTradeAccount.getExternalCapitalAccount().getId();
		add(linkTo(methodOn(ExternalCapitalAccountController.class).readExternalCapitalAccount(
				externalTradeAccount.getExternalCapitalAccount().getFund().getId(), externalCapitalAccountId))
						.withRel("externalCapitalAccount"));
	}

	public ExternalTradeAccountType getExternalTradeAccountType() {
		return externalTradeAccountType;
	}

	public String getExternalTradeAccount() {
		return externalTradeAccount;
	}

}

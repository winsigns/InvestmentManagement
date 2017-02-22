package com.winsigns.investment.fundService.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.hateoas.Resource;

import com.winsigns.investment.fundService.controller.ExternalCapitalAccountController;
import com.winsigns.investment.fundService.dictionary.ExternalTradeAccountType;
import com.winsigns.investment.fundService.model.ExternalCapitalAccount;
import com.winsigns.investment.fundService.model.ExternalTradeAccount;
import com.winsigns.investment.fundService.model.OpenedInvestmentScope;

public class ExternalTradeAccountResource extends Resource<ExternalTradeAccount> {

	private final ExternalTradeAccountType externalTradeAccountType;

	private final String externalTradeAccount;

	private final List<OpenedInvestmentScope> openedInvestmentScopes;

	private final ExternalCapitalAccount externalCapitalAccount;

	public ExternalTradeAccountResource(ExternalTradeAccount externalTradeAccount) {
		super(externalTradeAccount);
		this.externalCapitalAccount = externalTradeAccount.getExternalCapitalAccount();
		this.externalTradeAccount = externalTradeAccount.getExternalTradeAccount();
		this.externalTradeAccountType = externalTradeAccount.getExternalTradeAccountType();
		this.openedInvestmentScopes = externalTradeAccount.getOpenedInvestmentScopes();

		Long externalCapitalAccountId = externalCapitalAccount.getId();
		add(linkTo(methodOn(ExternalCapitalAccountController.class)
				.readExternalCapitalAccount(externalCapitalAccount.getFund().getId(), externalCapitalAccountId))
						.withRel("externalCapitalAccount"));
	}

	public ExternalTradeAccountType getExternalTradeAccountType() {
		return externalTradeAccountType;
	}

	public String getExternalTradeAccount() {
		return externalTradeAccount;
	}

	public List<OpenedInvestmentScope> getOpenedInvestmentScopes() {
		return openedInvestmentScopes;
	}

	public ExternalCapitalAccount getExternalCapitalAccount() {
		return externalCapitalAccount;
	}

}

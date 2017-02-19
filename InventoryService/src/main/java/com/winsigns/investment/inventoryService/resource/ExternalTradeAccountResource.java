package com.winsigns.investment.inventoryService.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.hateoas.Resource;

import com.winsigns.investment.inventoryService.controller.ExternalCapitalAccountController;
import com.winsigns.investment.inventoryService.model.ExternalCapitalAccount;
import com.winsigns.investment.inventoryService.model.ExternalTradeAccount;
import com.winsigns.investment.inventoryService.model.ExternalTradeAccountType;
import com.winsigns.investment.inventoryService.model.InvestmentScope;

public class ExternalTradeAccountResource extends Resource<ExternalTradeAccount> {

	private final ExternalTradeAccountType externalTradeAccountType;

	private final String externalTradeAccount;

	private final List<InvestmentScope> openedInvestmentScopes;

	private final ExternalCapitalAccount externalCapitalAccount;

	public ExternalTradeAccountResource(ExternalTradeAccount externalTradeAccount) {
		super(externalTradeAccount);
		this.externalCapitalAccount = externalTradeAccount.getExternalCapitalAccount();
		this.externalTradeAccount = externalTradeAccount.getExternalTradeAccount();
		this.externalTradeAccountType = externalTradeAccount.getExternalTradeAccountType();
		this.openedInvestmentScopes = externalTradeAccount.getOpenedInvestmentScopes();

		Long externalCapitalAccountId = externalCapitalAccount.getId();
		add(linkTo(methodOn(ExternalCapitalAccountController.class)
				.readExternalCapitalAccount(externalCapitalAccount.getFundId(), externalCapitalAccountId))
						.withRel("externalCapitalAccount"));
	}

	public ExternalTradeAccountType getExternalTradeAccountType() {
		return externalTradeAccountType;
	}

	public String getExternalTradeAccount() {
		return externalTradeAccount;
	}

	public List<InvestmentScope> getOpenedInvestmentScopes() {
		return openedInvestmentScopes;
	}

	public ExternalCapitalAccount getExternalCapitalAccount() {
		return externalCapitalAccount;
	}

}

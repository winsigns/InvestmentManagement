package com.winsigns.investment.fundService.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.hateoas.Resource;

import com.winsigns.investment.fundService.constant.ExternalCapitalAccountType;
import com.winsigns.investment.fundService.constant.ExternalOpenOrganization;
import com.winsigns.investment.fundService.controller.ExternalTradeAccountController;
import com.winsigns.investment.fundService.controller.FundController;
import com.winsigns.investment.fundService.model.ExternalCapitalAccount;
import com.winsigns.investment.fundService.model.ExternalTradeAccount;
import com.winsigns.investment.fundService.model.Fund;

public class ExternalCapitalAccountResource extends Resource<ExternalCapitalAccount> {

	private final Fund fund;

	private final ExternalCapitalAccountType externalCapitalAccountType;

	private final String externalCapitalAccount;

	private final ExternalOpenOrganization externalOpenOrganization;

	private final List<ExternalTradeAccount> externalTradeAccounts;

	public ExternalCapitalAccountResource(ExternalCapitalAccount externalCapitalAccount) {
		super(externalCapitalAccount);
		this.fund = externalCapitalAccount.getFund();
		this.externalCapitalAccountType = externalCapitalAccount.getExternalCapitalAccountType();
		this.externalCapitalAccount = externalCapitalAccount.getExternalCapitalAccount();
		this.externalOpenOrganization = externalCapitalAccount.getExternalOpenOrganization();
		this.externalTradeAccounts = externalCapitalAccount.getExternalTradeAccounts();

		add(linkTo(methodOn(ExternalTradeAccountController.class).readExternalTradeAccounts(fund.getId(),
				externalCapitalAccount.getId())).withRel("externalTradeAccounts"));
		add(linkTo(methodOn(FundController.class).readFund(fund.getId())).withRel("fund"));
	}

	public Fund getFund() {
		return fund;
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

	public List<ExternalTradeAccount> getExternalTradeAccounts() {
		return externalTradeAccounts;
	}

}

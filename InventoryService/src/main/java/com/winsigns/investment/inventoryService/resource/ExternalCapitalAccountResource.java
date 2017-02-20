package com.winsigns.investment.inventoryService.resource;

import java.util.List;

import org.springframework.hateoas.Resource;

import com.winsigns.investment.inventoryService.model.ExternalCapitalAccount;
import com.winsigns.investment.inventoryService.model.ExternalCapitalAccountType;
import com.winsigns.investment.inventoryService.model.ExternalOpenOrganization;
import com.winsigns.investment.inventoryService.model.ExternalTradeAccount;

public class ExternalCapitalAccountResource extends Resource<ExternalCapitalAccount> {

	// 名称
	private final Long fundId;

	private final ExternalCapitalAccountType externalCapitalAccountType;

	private final String externalCapitalAccount;

	private final ExternalOpenOrganization externalOpenOrganization;

	private final List<ExternalTradeAccount> externalTradeAccounts;

	public ExternalCapitalAccountResource(ExternalCapitalAccount externalCapitalAccount) {
		super(externalCapitalAccount);
		this.fundId = externalCapitalAccount.getFundId();
		this.externalCapitalAccountType = externalCapitalAccount.getExternalCapitalAccountType();
		this.externalCapitalAccount = externalCapitalAccount.getExternalCapitalAccount();
		this.externalOpenOrganization = externalCapitalAccount.getExternalOpenOrganization();
		this.externalTradeAccounts = externalCapitalAccount.getExternalTradeAccounts();
	}

	public Long getFundId() {
		return fundId;
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

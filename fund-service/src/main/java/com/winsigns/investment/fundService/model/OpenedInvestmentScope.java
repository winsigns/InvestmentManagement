package com.winsigns.investment.fundService.model;

import javax.persistence.ManyToOne;

import com.winsigns.investment.frame.model.AbstractEntity;

public class OpenedInvestmentScope extends AbstractEntity {

	@ManyToOne
	private ExternalTradeAccount externalTradeAccount;

	private String name;

	public ExternalTradeAccount getExternalTradeAccount() {
		return externalTradeAccount;
	}

	public void setExternalTradeAccount(ExternalTradeAccount externalTradeAccount) {
		this.externalTradeAccount = externalTradeAccount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

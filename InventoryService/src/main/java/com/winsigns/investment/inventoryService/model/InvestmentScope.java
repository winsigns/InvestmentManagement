package com.winsigns.investment.inventoryService.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.winsigns.investment.frame.model.DictionaryAbstractEntity;

@Entity
public class InvestmentScope extends DictionaryAbstractEntity {
	private String name;

	@ManyToOne
	private ExternalTradeAccountType externalTradeAccountType;

	@ManyToMany(mappedBy = "openedInvestmentScopes")
	private List<ExternalTradeAccount> usedExternalTradeAccounts = new ArrayList<ExternalTradeAccount>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ExternalTradeAccountType getExternalTradeAccountType() {
		return externalTradeAccountType;
	}

	public void setExternalTradeAccountType(ExternalTradeAccountType externalTradeAccountType) {
		this.externalTradeAccountType = externalTradeAccountType;
	}

}

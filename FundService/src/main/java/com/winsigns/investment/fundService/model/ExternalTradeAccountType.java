package com.winsigns.investment.fundService.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.winsigns.investment.frame.model.DictionaryAbstractEntity;

@Entity
public class ExternalTradeAccountType extends DictionaryAbstractEntity {
	// 名称
	private String name;

	@OneToOne
	private ExternalCapitalAccountType externalCapitalAccountType;

	@OneToOne
	private Currency supportExternalCapitalAccountCurrency;

	@OneToMany(mappedBy = "externalTradeAccountType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<InvestmentScope> supportInvestmentScopes = new ArrayList<InvestmentScope>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ExternalCapitalAccountType getExternalCapitalAccountType() {
		return externalCapitalAccountType;
	}

	public void setExternalCapitalAccountType(ExternalCapitalAccountType externalCapitalAccountType) {
		this.externalCapitalAccountType = externalCapitalAccountType;
	}

	public Currency getSupportExternalCapitalAccountCurrency() {
		return supportExternalCapitalAccountCurrency;
	}

	public void setSupportExternalCapitalAccountCurrency(Currency supportExternalCapitalAccountCurrency) {
		this.supportExternalCapitalAccountCurrency = supportExternalCapitalAccountCurrency;
	}

}

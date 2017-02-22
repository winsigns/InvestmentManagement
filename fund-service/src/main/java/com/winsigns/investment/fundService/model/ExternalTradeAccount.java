package com.winsigns.investment.fundService.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.winsigns.investment.frame.model.AbstractEntity;

@Entity
public class ExternalTradeAccount extends AbstractEntity {

	private Long externalTradeAccountTypeId;

	private String externalTradeAccount;

	// 开通的投资范围
	@OneToMany(mappedBy = "externalTradeAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<OpenedInvestmentScope> openedInvestmentScopes = new ArrayList<OpenedInvestmentScope>();

	@ManyToOne
	@JsonIgnore
	private ExternalCapitalAccount externalCapitalAccount;

	public Long getExternalTradeAccountTypeId() {
		return externalTradeAccountTypeId;
	}

	public void setExternalTradeAccountTypeId(Long externalTradeAccountTypeId) {
		this.externalTradeAccountTypeId = externalTradeAccountTypeId;
	}

	public String getExternalTradeAccount() {
		return externalTradeAccount;
	}

	public void setExternalTradeAccount(String externalTradeAccount) {
		this.externalTradeAccount = externalTradeAccount;
	}

	public List<OpenedInvestmentScope> getOpenedInvestmentScopes() {
		return openedInvestmentScopes;
	}

	public void setOpenedInvestmentScopes(List<OpenedInvestmentScope> openedInvestmentScopes) {
		this.openedInvestmentScopes = openedInvestmentScopes;
	}

	public ExternalCapitalAccount getExternalCapitalAccount() {
		return externalCapitalAccount;
	}

	public void setExternalCapitalAccount(ExternalCapitalAccount externalCapitalAccount) {
		this.externalCapitalAccount = externalCapitalAccount;
	}

}

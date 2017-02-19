package com.winsigns.investment.inventoryService.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.winsigns.investment.frame.model.AbstractEntity;

@Entity
public class ExternalTradeAccount extends AbstractEntity {

	@OneToOne
	private ExternalTradeAccountType externalTradeAccountType;

	private String externalTradeAccount;

	// 开通的投资范围
	@JoinTable(name = "external_trade_account_to_investment_scope", joinColumns = {
			@JoinColumn(name = "external_trade_account_id") }, inverseJoinColumns = {
					@JoinColumn(name = "investment_scope_id") })
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<InvestmentScope> openedInvestmentScopes = new ArrayList<InvestmentScope>();

	@ManyToOne
	private ExternalCapitalAccount externalCapitalAccount;

	public ExternalTradeAccountType getExternalTradeAccountType() {
		return externalTradeAccountType;
	}

	public void setExternalTradeAccountType(ExternalTradeAccountType externalTradeAccountType) {
		this.externalTradeAccountType = externalTradeAccountType;
	}

	public String getExternalTradeAccount() {
		return externalTradeAccount;
	}

	public void setExternalTradeAccount(String externalTradeAccount) {
		this.externalTradeAccount = externalTradeAccount;
	}

	public List<InvestmentScope> getOpenedInvestmentScopes() {
		return openedInvestmentScopes;
	}

	public void setOpenedInvestmentScopes(List<InvestmentScope> openedInvestmentScopes) {
		this.openedInvestmentScopes = openedInvestmentScopes;
	}

	public ExternalCapitalAccount getExternalCapitalAccount() {
		return externalCapitalAccount;
	}

	public void setExternalCapitalAccount(ExternalCapitalAccount externalCapitalAccount) {
		this.externalCapitalAccount = externalCapitalAccount;
	}

}

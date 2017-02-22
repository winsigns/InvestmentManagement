/**
 * 
 */
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
public class ExternalCapitalAccount extends AbstractEntity {
	// 外部资金账户类型
	private int externalCapitalAccountTypeId;

	// 外部开户机构
	private int externalOpenOrganizationId;

	// 基金
	@ManyToOne
	@JsonIgnore
	private Fund fund;

	// 账号
	private String externalCapitalAccount;

	// 外部交易账户
	@OneToMany(mappedBy = "externalCapitalAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<ExternalTradeAccount> externalTradeAccounts = new ArrayList<ExternalTradeAccount>();

	public Fund getFund() {
		return fund;
	}

	public void setFund(Fund fund) {
		this.fund = fund;
	}

	public int getExternalCapitalAccountTypeId() {
		return externalCapitalAccountTypeId;
	}

	public void setExternalCapitalAccountTypeId(int externalCapitalAccountTypeId) {
		this.externalCapitalAccountTypeId = externalCapitalAccountTypeId;
	}

	public int getExternalOpenOrganizationId() {
		return externalOpenOrganizationId;
	}

	public void setExternalOpenOrganizationId(int externalOpenOrganizationId) {
		this.externalOpenOrganizationId = externalOpenOrganizationId;
	}

	public String getExternalCapitalAccount() {
		return externalCapitalAccount;
	}

	public void setExternalCapitalAccount(String externalCapitalAccount) {
		this.externalCapitalAccount = externalCapitalAccount;
	}

	public List<ExternalTradeAccount> getExternalTradeAccounts() {
		return externalTradeAccounts;
	}

	public void setExternalTradeAccounts(List<ExternalTradeAccount> externalTradeAccounts) {
		this.externalTradeAccounts = externalTradeAccounts;
	}
}

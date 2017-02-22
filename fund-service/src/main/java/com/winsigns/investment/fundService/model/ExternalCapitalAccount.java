/**
 * 
 */
package com.winsigns.investment.fundService.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.winsigns.investment.frame.model.AbstractEntity;
import com.winsigns.investment.fundService.constant.ExternalCapitalAccountType;
import com.winsigns.investment.fundService.constant.ExternalOpenOrganization;

@Entity
public class ExternalCapitalAccount extends AbstractEntity {
	// 外部资金账户类型
	@Enumerated(EnumType.STRING)
	private ExternalCapitalAccountType externalCapitalAccountType;

	// 外部开户机构
	@Enumerated(EnumType.STRING)
	private ExternalOpenOrganization externalOpenOrganization;

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

	public ExternalCapitalAccountType getExternalCapitalAccountType() {
		return externalCapitalAccountType;
	}

	public void setExternalCapitalAccountType(ExternalCapitalAccountType externalCapitalAccountType) {
		this.externalCapitalAccountType = externalCapitalAccountType;
	}

	public ExternalOpenOrganization getExternalOpenOrganization() {
		return externalOpenOrganization;
	}

	public void setExternalOpenOrganization(ExternalOpenOrganization externalOpenOrganization) {
		this.externalOpenOrganization = externalOpenOrganization;
	}

	public Fund getFund() {
		return fund;
	}

	public void setFund(Fund fund) {
		this.fund = fund;
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

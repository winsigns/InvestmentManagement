/**
 * 
 */
package com.winsigns.investment.inventoryService.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.winsigns.investment.frame.model.AbstractEntity;

@Entity
public class ExternalCapitalAccount extends AbstractEntity {

	// 关联的基金产品ID
	private Long fundId;

	// 外部资金账户类型
	@OneToOne
	private ExternalCapitalAccountType externalCapitalAccountType;

	// 外部开户机构
	@OneToOne
	private ExternalOpenOrganization externalOpenOrganization;

	// 账号
	private String externalCapitalAccount;

	// 外部交易账户
	@OneToMany(mappedBy = "externalCapitalAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ExternalTradeAccount> externalTradeAccounts = new ArrayList<ExternalTradeAccount>();

	public Long getFundId() {
		return fundId;
	}

	public void setFundId(Long fundId) {
		this.fundId = fundId;
	}

	public ExternalCapitalAccountType getExternalCapitalAccountType() {
		return externalCapitalAccountType;
	}

	public void setExternalCapitalAccountType(ExternalCapitalAccountType externalCapitalAccountType) {
		this.externalCapitalAccountType = externalCapitalAccountType;
	}

	public String getExternalCapitalAccount() {
		return externalCapitalAccount;
	}

	public void setExternalCapitalAccount(String externalCapitalAccount) {
		this.externalCapitalAccount = externalCapitalAccount;
	}

	public ExternalOpenOrganization getExternalOpenOrganization() {
		return externalOpenOrganization;
	}

	public void setExternalOpenOrganization(ExternalOpenOrganization externalOpenOrganization) {
		this.externalOpenOrganization = externalOpenOrganization;
	}

	public List<ExternalTradeAccount> getExternalTradeAccounts() {
		return externalTradeAccounts;
	}

	public void setExternalTradeAccounts(List<ExternalTradeAccount> externalTradeAccounts) {
		this.externalTradeAccounts = externalTradeAccounts;
	}
}

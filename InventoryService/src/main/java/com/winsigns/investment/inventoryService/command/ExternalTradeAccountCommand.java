package com.winsigns.investment.inventoryService.command;

import java.util.List;

public class ExternalTradeAccountCommand {

	private Long extTradeAccountTypeId;

	private String externalTradeAccount;

	private List<Long> openedInvestmentScopeIds;

	public Long getExtTradeAccountTypeId() {
		return extTradeAccountTypeId;
	}

	public void setExtTradeAccountTypeId(Long extTradeAccountTypeId) {
		this.extTradeAccountTypeId = extTradeAccountTypeId;
	}

	public String getExternalTradeAccount() {
		return externalTradeAccount;
	}

	public void setExternalTradeAccount(String externalTradeAccount) {
		this.externalTradeAccount = externalTradeAccount;
	}

	public List<Long> getOpenedInvestmentScopeIds() {
		return openedInvestmentScopeIds;
	}

	public void setOpenedInvestmentScopeIds(List<Long> openedInvestmentScopeIds) {
		this.openedInvestmentScopeIds = openedInvestmentScopeIds;
	}

}

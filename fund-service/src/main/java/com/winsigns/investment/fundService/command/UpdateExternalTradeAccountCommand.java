package com.winsigns.investment.fundService.command;

import com.winsigns.investment.fundService.constant.ExternalTradeAccountType;

public class UpdateExternalTradeAccountCommand {

	private ExternalTradeAccountType externalTradeAccountType;

	private String externalTradeAccount;

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

}

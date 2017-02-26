package com.winsigns.investment.fundService.command;

import com.winsigns.investment.fundService.constant.ExternalTradeAccountType;

public class ExternalTradeAccountCommand {

	private ExternalTradeAccountType externalTradeAccountType;

	private String accountNo;

	public ExternalTradeAccountType getExternalTradeAccountType() {
		return externalTradeAccountType;
	}

	public void setExternalTradeAccountType(ExternalTradeAccountType externalTradeAccountType) {
		this.externalTradeAccountType = externalTradeAccountType;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String externalTradeAccount) {
		this.accountNo = externalTradeAccount;
	}

}

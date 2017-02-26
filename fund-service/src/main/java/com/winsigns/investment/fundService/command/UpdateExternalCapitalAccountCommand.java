package com.winsigns.investment.fundService.command;

import com.winsigns.investment.fundService.constant.ExternalCapitalAccountType;
import com.winsigns.investment.fundService.constant.ExternalOpenOrganization;

public class UpdateExternalCapitalAccountCommand {
	// 外部资金账户类型序号
	private ExternalCapitalAccountType externalCapitalAccountType;

	// 账号
	private String accountNo;

	// 开户经纪商序号
	private ExternalOpenOrganization externalOpenOrganization;

	public ExternalCapitalAccountType getExternalCapitalAccountType() {
		return externalCapitalAccountType;
	}

	public void setExternalCapitalAccountType(ExternalCapitalAccountType externalCapitalAccountType) {
		this.externalCapitalAccountType = externalCapitalAccountType;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String externalCapitalAccount) {
		this.accountNo = externalCapitalAccount;
	}

	public ExternalOpenOrganization getExternalOpenOrganization() {
		return externalOpenOrganization;
	}

	public void setExternalOpenOrganization(ExternalOpenOrganization externalOpenOrganization) {
		this.externalOpenOrganization = externalOpenOrganization;
	}

}

package com.winsigns.investment.fundService.command;

public class ExternalCapitalAccountCommand {
	// 外部资金账户类型序号
	private Long externalCapitalAccountTypeId;

	// 账号
	private String externalCapitalAccount;

	// 开户经纪商序号
	private Long externalOpenOrganizationId;

	public Long getExternalCapitalAccountTypeId() {
		return externalCapitalAccountTypeId;
	}

	public void setExternalCapitalAccountTypeId(Long externalCapitalAccountTypeId) {
		this.externalCapitalAccountTypeId = externalCapitalAccountTypeId;
	}

	public String getExternalCapitalAccount() {
		return externalCapitalAccount;
	}

	public void setExternalCapitalAccount(String externalCapitalAccount) {
		this.externalCapitalAccount = externalCapitalAccount;
	}

	public Long getExternalOpenOrganizationId() {
		return externalOpenOrganizationId;
	}

	public void setExternalOpenOrganizationId(Long externalOpenOrganizationId) {
		this.externalOpenOrganizationId = externalOpenOrganizationId;
	}

}

package com.winsigns.investment.inventoryService.command;

public class CapitalChangeCommand {
	private Long currencyId;

	private Double changedCapital;

	private Long externalCapitalAccountTypeId;

	public Long getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}

	public Double getChangedCapital() {
		return changedCapital;
	}

	public void setChangedCapital(Double changedCapital) {
		this.changedCapital = changedCapital;
	}

	public Long getExternalCapitalAccountTypeId() {
		return externalCapitalAccountTypeId;
	}

	public void setExternalCapitalAccountTypeId(Long externalCapitalAccountTypeId) {
		this.externalCapitalAccountTypeId = externalCapitalAccountTypeId;
	}

}

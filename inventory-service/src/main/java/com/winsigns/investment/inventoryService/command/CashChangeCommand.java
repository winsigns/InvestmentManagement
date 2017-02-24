package com.winsigns.investment.inventoryService.command;

import java.util.Currency;

public class CashChangeCommand {
    private Currency currency;

    private Double changedCapital;

    private Long externalCapitalAccountTypeId;

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
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

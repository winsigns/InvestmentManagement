package com.winsigns.investment.inventoryService.command;

import java.util.Currency;

public class CreateFundAccountCapitalCommand {
    private Long fundAccountId;

    private String externalCapitalAccountType;

    private Currency currency;

    public Long getFundAccountId() {
        return fundAccountId;
    }

    public void setFundAccountId(Long fundAccountId) {
        this.fundAccountId = fundAccountId;
    }

    public String getExternalCapitalAccountType() {
        return externalCapitalAccountType;
    }

    public void setExternalCapitalAccountTypeId(String externalCapitalAccountType) {
        this.externalCapitalAccountType = externalCapitalAccountType;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

}

package com.winsigns.investment.inventoryService.command;

public class CreateFundAccountCapitalDetailCommand {
    private Long fundAccountCapitalId;

    private Long externalCapitalAccountId;

    public Long getFundAccountCapitalId() {
        return fundAccountCapitalId;
    }

    public void setFundAccountCapitalId(Long fundAccountCapitalId) {
        this.fundAccountCapitalId = fundAccountCapitalId;
    }

    public Long getExternalCapitalAccountId() {
        return externalCapitalAccountId;
    }

    public void setExternalCapitalAccountId(Long externalCapitalAccountId) {
        this.externalCapitalAccountId = externalCapitalAccountId;
    }

}

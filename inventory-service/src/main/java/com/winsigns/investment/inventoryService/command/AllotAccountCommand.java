package com.winsigns.investment.inventoryService.command;

public class AllotAccountCommand {

    private Double changedCapital;

    private Long matchECACashPoolId;

    public Double getChangedCapital() {
        return changedCapital;
    }

    public void setChangedCapital(Double changedCapital) {
        this.changedCapital = changedCapital;
    }

    public Long getMatchECACashPoolId() {
        return matchECACashPoolId;
    }

    public void setMatchECACashPoolId(Long matchEexternalCapitalAccountId) {
        this.matchECACashPoolId = matchEexternalCapitalAccountId;
    }

}

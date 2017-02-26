package com.winsigns.investment.inventoryService.command;

public class EnfeoffAccountCommand {

    private Long matchFACapitalDetailId;

    private Double assignedCash;

    public Long getMatchFACapitalDetailId() {
        return matchFACapitalDetailId;
    }

    public void setMatchFACapitalDetailId(Long matchFACapitalDetailId) {
        this.matchFACapitalDetailId = matchFACapitalDetailId;
    }

    public Double getAssignedCash() {
        return assignedCash;
    }

    public void setAssignedCash(Double assignedCash) {
        this.assignedCash = assignedCash;
    }

}

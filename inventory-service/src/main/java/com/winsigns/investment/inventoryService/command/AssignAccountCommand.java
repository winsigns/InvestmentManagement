package com.winsigns.investment.inventoryService.command;

public class AssignAccountCommand {
    private Long ecaCashPoolId;

    private Double assignedCash;

    public Long getEcaCashPoolId() {
        return ecaCashPoolId;
    }

    public void setEcaCashPoolId(Long ecaCashPoolId) {
        this.ecaCashPoolId = ecaCashPoolId;
    }

    public Double getAssignedCash() {
        return assignedCash;
    }

    public void setAssignedCash(Double assignedCash) {
        this.assignedCash = assignedCash;
    }

}

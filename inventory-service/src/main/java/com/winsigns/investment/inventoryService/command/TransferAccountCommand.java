package com.winsigns.investment.inventoryService.command;

import java.util.Currency;

public class TransferAccountCommand {

  private Currency currency;

  private Double changedCapital;

  private Long matchEexternalCapitalAccountId;

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

  public Long getMatchEexternalCapitalAccountId() {
    return matchEexternalCapitalAccountId;
  }

  public void setMatchEexternalCapitalAccountId(Long matchEexternalCapitalAccountId) {
    this.matchEexternalCapitalAccountId = matchEexternalCapitalAccountId;
  }

}

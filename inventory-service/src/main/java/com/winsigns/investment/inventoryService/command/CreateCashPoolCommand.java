package com.winsigns.investment.inventoryService.command;

import java.util.Currency;

public class CreateCashPoolCommand {

  private Currency currency;

  private Long fundId;

  private Long externalCapitalAccountId;

  public Currency getCurrency() {
    return currency;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }

  public Long getFundId() {
    return fundId;
  }

  public void setFundId(Long fundId) {
    this.fundId = fundId;
  }

  public Long getExternalCapitalAccountId() {
    return externalCapitalAccountId;
  }

  public void setExternalCapitalAccountId(Long externalCapitalAccountId) {
    this.externalCapitalAccountId = externalCapitalAccountId;
  }

}

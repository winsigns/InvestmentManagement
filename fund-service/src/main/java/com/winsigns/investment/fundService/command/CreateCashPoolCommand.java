package com.winsigns.investment.fundService.command;

import java.util.Currency;

public class CreateCashPoolCommand {

  private Currency currency;

  private Long externalCapitalAccountId;

  public Currency getCurrency() {
    return currency;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }

  public Long getExternalCapitalAccountId() {
    return externalCapitalAccountId;
  }

  public void setExternalCapitalAccountId(Long externalCapitalAccountId) {
    this.externalCapitalAccountId = externalCapitalAccountId;
  }

}

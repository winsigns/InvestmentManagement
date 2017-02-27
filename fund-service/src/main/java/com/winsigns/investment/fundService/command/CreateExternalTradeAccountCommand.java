package com.winsigns.investment.fundService.command;

import com.winsigns.investment.fundService.constant.ExternalTradeAccountType;

public class CreateExternalTradeAccountCommand {

  private Long externalCapitalAccountId;

  private ExternalTradeAccountType accountType;

  private String accountNo;

  public Long getExternalCapitalAccountId() {
    return externalCapitalAccountId;
  }

  public void setExternalCapitalAccountId(Long externalCapitalAccountId) {
    this.externalCapitalAccountId = externalCapitalAccountId;
  }

  public ExternalTradeAccountType getAccountType() {
    return accountType;
  }

  public void setAccountType(ExternalTradeAccountType accountType) {
    this.accountType = accountType;
  }

  public String getAccountNo() {
    return accountNo;
  }

  public void setAccountNo(String externalTradeAccount) {
    this.accountNo = externalTradeAccount;
  }

}

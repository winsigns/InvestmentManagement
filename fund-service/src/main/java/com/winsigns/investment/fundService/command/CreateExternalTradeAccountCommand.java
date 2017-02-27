package com.winsigns.investment.fundService.command;

import com.winsigns.investment.fundService.constant.ExternalTradeAccountType;

public class CreateExternalTradeAccountCommand {

  private ExternalTradeAccountType accountType;

  private String accountNo;

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

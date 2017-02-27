package com.winsigns.investment.fundService.command;

import com.winsigns.investment.fundService.constant.ExternalCapitalAccountType;
import com.winsigns.investment.fundService.constant.ExternalOpenOrganization;

public class CreateExternalCapitalAccountCommand {

  // 基金产品
  private Long fundId;

  // 外部资金账户类型序号
  private ExternalCapitalAccountType accountType;

  // 账号
  private String accountNo;

  // 开户经纪商
  private ExternalOpenOrganization externalOpenOrganization;

  public Long getFundId() {
    return fundId;
  }

  public void setFundId(Long fundId) {
    this.fundId = fundId;
  }

  public ExternalCapitalAccountType getAccountType() {
    return accountType;
  }

  public void setAccountType(ExternalCapitalAccountType accountType) {
    this.accountType = accountType;
  }

  public String getAccountNo() {
    return accountNo;
  }

  public void setAccountNo(String externalCapitalAccount) {
    this.accountNo = externalCapitalAccount;
  }

  public ExternalOpenOrganization getExternalOpenOrganization() {
    return externalOpenOrganization;
  }

  public void setExternalOpenOrganization(ExternalOpenOrganization externalOpenOrganization) {
    this.externalOpenOrganization = externalOpenOrganization;
  }

}

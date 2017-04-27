/**
 *
 */
package com.winsigns.investment.fundService.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.winsigns.investment.framework.model.AbstractEntity;
import com.winsigns.investment.fundService.constant.ExternalCapitalAccountType;
import com.winsigns.investment.fundService.constant.ExternalOpenOrganization;

@Entity
public class ExternalCapitalAccount extends AbstractEntity {

  // 外部资金账户类型
  @Enumerated(EnumType.STRING)
  private ExternalCapitalAccountType accountType;

  // 外部开户机构
  @Enumerated(EnumType.STRING)
  private ExternalOpenOrganization externalOpenOrganization;

  // 基金
  @ManyToOne
  @JsonIgnore
  private Fund fund;

  // 账号
  private String accountNo;

  // 外部交易账户
  @OneToMany(mappedBy = "externalCapitalAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonIgnore
  private List<ExternalTradeAccount> externalTradeAccounts = new ArrayList<ExternalTradeAccount>();

  public ExternalCapitalAccountType getAccountType() {
    return accountType;
  }

  public void setAccountType(ExternalCapitalAccountType accountType) {
    this.accountType = accountType;
  }

  public ExternalOpenOrganization getExternalOpenOrganization() {
    return externalOpenOrganization;
  }

  public void setExternalOpenOrganization(ExternalOpenOrganization externalOpenOrganization) {
    this.externalOpenOrganization = externalOpenOrganization;
  }

  public Fund getFund() {
    return fund;
  }

  public void setFund(Fund fund) {
    this.fund = fund;
  }

  public String getAccountNo() {
    return accountNo;
  }

  public void setAccountNo(String externalCapitalAccount) {
    this.accountNo = externalCapitalAccount;
  }

  public List<ExternalTradeAccount> getExternalTradeAccounts() {
    return externalTradeAccounts;
  }

  public void setExternalTradeAccounts(List<ExternalTradeAccount> externalTradeAccounts) {
    this.externalTradeAccounts = externalTradeAccounts;
  }

}

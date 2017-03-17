package com.winsigns.investment.fundService.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.winsigns.investment.fundService.constant.ExternalTradeAccountType;
import com.winsigns.investment.framework.model.AbstractEntity;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import org.springframework.hateoas.core.Relation;

@Entity
@Relation(value = "external-trade-account", collectionRelation = "external-trade-accounts")
public class ExternalTradeAccount extends AbstractEntity {

  @Enumerated(EnumType.STRING)
  private ExternalTradeAccountType accountType;

  private String accountNo;

  @ManyToOne
  @JsonIgnore
  private ExternalCapitalAccount externalCapitalAccount;

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

  public ExternalCapitalAccount getExternalCapitalAccount() {
    return externalCapitalAccount;
  }

  public void setExternalCapitalAccount(ExternalCapitalAccount externalCapitalAccount) {
    this.externalCapitalAccount = externalCapitalAccount;
  }

}

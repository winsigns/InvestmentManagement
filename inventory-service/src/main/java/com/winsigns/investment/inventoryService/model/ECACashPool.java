/**
 * 
 */
package com.winsigns.investment.inventoryService.model;

import java.util.Currency;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.hateoas.core.Relation;

import com.winsigns.investment.framework.model.AbstractEntity;

@Entity
@Table(name = "external_capital_account_cash_pool")
@Relation(value = "eca-cash-pool", collectionRelation = "eca-cash-pools")
public class ECACashPool extends AbstractEntity {

  private Long externalCapitalAccountId;

  private Currency currency;

  private Double unassignedCapital;

  public Long getExternalCapitalAccountId() {
    return externalCapitalAccountId;
  }

  public void setExternalCapitalAccountId(Long externalCapitalAccountId) {
    this.externalCapitalAccountId = externalCapitalAccountId;
  }

  public Currency getCurrency() {
    return currency;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }

  public Double getUnassignedCapital() {
    return unassignedCapital;
  }

  public void setUnassignedCapital(Double unassignedCapital) {
    this.unassignedCapital = unassignedCapital;
  }

}

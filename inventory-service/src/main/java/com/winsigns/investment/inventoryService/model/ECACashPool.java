/**
 * 
 */
package com.winsigns.investment.inventoryService.model;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.winsigns.investment.framework.measure.MeasureHost;
import com.winsigns.investment.framework.measure.MeasureHostType;
import com.winsigns.investment.framework.spring.SpringManager;
import com.winsigns.investment.inventoryService.measure.ECACashPoolMHT;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "external_capital_account_cash_pool")
@Relation(value = "eca-cash-pool", collectionRelation = "eca-cash-pools")
public class ECACashPool extends MeasureHost {

  /*
   * 外部资金账户序号
   */
  @Getter
  @Setter
  private Long externalCapitalAccountId;

  /*
   * 币种
   */
  @Getter
  @Setter
  private Currency currency;

  /*
   * 未分配资金
   */
  @Getter
  @Setter
  private Double unassignedCapital;

  @Override
  public MeasureHostType getType() {
    return SpringManager.getApplicationContext().getBean(ECACashPoolMHT.class);
  }

  @OneToMany(mappedBy = "ecaCashPool", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonIgnore
  @Getter
  @Setter
  private List<ECACashSerial> ecaCashSerials = new ArrayList<ECACashSerial>();

}

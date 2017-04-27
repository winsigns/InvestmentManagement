/**
 * 
 */
package com.winsigns.investment.inventoryService.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.winsigns.investment.framework.measure.MeasureHost;
import com.winsigns.investment.framework.measure.MeasureHostType;
import com.winsigns.investment.inventoryService.constant.CurrencyCode;
import com.winsigns.investment.inventoryService.measure.ECACashPoolMHT;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "external_capital_account_cash_pool")
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
  @Enumerated(EnumType.STRING)
  private CurrencyCode currency;

  /*
   * 未分配资金
   */
  @Getter
  @Setter
  private Double unassignedCapital = 0.0;

  /*
   * 该池子分配出去的资金
   */
  @Getter
  @JsonIgnore
  @OneToMany(mappedBy = "cashPool", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<CapitalDetail> details = new ArrayList<CapitalDetail>();

  public Double changeUnassignedCapital(Double unassignedCapital) {
    this.unassignedCapital += unassignedCapital;
    return this.unassignedCapital;
  }

  @Override
  protected Class<? extends MeasureHostType> defineType() {
    return ECACashPoolMHT.class;
  }

  @Override
  public MeasureHost parent() {
    return null;
  }

}

package com.winsigns.investment.inventoryService.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.winsigns.investment.framework.measure.MeasureHost;
import com.winsigns.investment.framework.measure.MeasureHostType;
import com.winsigns.investment.inventoryService.constant.CurrencyCode;
import com.winsigns.investment.inventoryService.measure.FACapitalDetailMHT;

import lombok.Getter;
import lombok.Setter;

@Entity
@Relation(value = "capital-detail", collectionRelation = "capital-details")
public class CapitalDetail extends MeasureHost {

  // 具体的资金服务的资金
  @ManyToOne
  @JsonIgnore
  @Getter
  @Setter
  private FundAccountCapitalPool capitalPool;

  // 外部资金账户id
  @Getter
  @Setter
  @JsonIgnore
  @ManyToOne
  private ECACashPool cashPool;

  /*
   * 币种
   */
  @Getter
  @Setter
  @Enumerated(EnumType.STRING)
  private CurrencyCode currency;

  // 现金
  @Getter
  @Setter
  private Double cash = 0.0;

  // 可用资金
  @Getter
  @Setter
  private Double availableCapital = 0.0;

  // 可取资金
  @Getter
  @Setter
  private Double desirableCapital = 0.0;

  @Override
  protected Class<? extends MeasureHostType> defineType() {
    return FACapitalDetailMHT.class;
  }

  @Override
  public MeasureHost parent() {
    // TODO Auto-generated method stub
    return null;
  }

  public Double changeCash(Double cash) {
    this.cash += cash;
    return this.cash;
  }

  public Double changeAvailableCapital(Double availableCapital) {
    this.availableCapital += availableCapital;
    return this.availableCapital;
  }

  public Double changeDesirableCapital(Double desirableCapital) {
    this.desirableCapital += desirableCapital;
    return this.desirableCapital;
  }
}

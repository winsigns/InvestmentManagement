package com.winsigns.investment.inventoryService.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.winsigns.investment.framework.measure.MeasureHost;
import com.winsigns.investment.framework.measure.MeasureHostType;
import com.winsigns.investment.inventoryService.constant.CurrencyCode;
import com.winsigns.investment.inventoryService.measure.FACapitalDetailMHT;

import lombok.Getter;
import lombok.Setter;

@Entity
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

  // 浮动现金
  @Getter
  @Setter
  private Double floatCash = 0.0;

  // 浮动可用资金
  @Getter
  @Setter
  private Double floatAvailableCapital = 0.0;

  // 浮动可取资金
  @Getter
  @Setter
  private Double floatDesirableCapital = 0.0;

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

  public Double changeFloatCash(Double floatCash) {
    this.floatCash += floatCash;
    return this.floatCash;
  }

  public Double changeFloatAvailableCapital(Double floatAvailableCapital) {
    this.floatAvailableCapital += floatAvailableCapital;
    return this.floatAvailableCapital;
  }

  public Double changeFloatDesirableCapital(Double floatDesirableCapital) {
    this.floatDesirableCapital += floatDesirableCapital;
    return this.floatDesirableCapital;
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

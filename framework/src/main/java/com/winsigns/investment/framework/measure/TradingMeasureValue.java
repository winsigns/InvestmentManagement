package com.winsigns.investment.framework.measure;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by colin on 2017/3/3.
 */
public class TradingMeasureValue extends MeasureValue {

  @Setter
  @Getter
  @JsonIgnore
  private boolean isFloat;

  @Setter
  @Getter
  @JsonIgnore
  private String version;

  public TradingMeasureValue(MeasureHost measureHost, Measure measure, boolean isFloat,
      String version, double value) {
    super(measureHost, measure, value);
    this.version = version;
    this.isFloat = isFloat;
  }
}

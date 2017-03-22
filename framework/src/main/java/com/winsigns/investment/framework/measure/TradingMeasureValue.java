package com.winsigns.investment.framework.measure;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by colin on 2017/3/3.
 */
public class TradingMeasureValue extends MeasureValue {

  @Setter
  @Getter
  private boolean isFloat;

  @Setter
  @Getter
  private String version;

  public TradingMeasureValue(MeasureHost measureHost, Measure measure, boolean isFloat,
      String version, Double value) {
    super(measureHost, measure, value);
    this.version = version;
    this.isFloat = isFloat;
  }
}

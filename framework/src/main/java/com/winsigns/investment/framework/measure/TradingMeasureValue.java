package com.winsigns.investment.framework.measure;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by colin on 2017/3/3.
 */
public class TradingMeasureValue extends MeasureValue {

  @Setter
  @Getter
  private Boolean isFloat;

  @Setter
  @Getter
  private String version;

  public TradingMeasureValue(MeasureHost measureHost, Measure measure, boolean isFloat,
      String version, Double value) {
    super(measureHost, measure, value);
    this.version = version;
    this.isFloat = isFloat;
  }

  @Override
  public String getKey() {
    return getMeasureHost().getType().getName() + ":" + getMeasureHost().getId() + ":"
        + getMeasure().getName() + ":" + isFloat + ":" + version;
  }

  @Override
  public String getIndex() {
    return getMeasureHost().getType().getName() + ":" + getMeasureHost().getId() + ":"
        + getMeasure().getName() + ":" + isFloat + ":index";
  }

}

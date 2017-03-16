package com.winsigns.investment.framework.measure;

import java.util.List;

import org.springframework.stereotype.Component;

import com.winsigns.investment.framework.model.AbstractEntity;

/**
 * Created by colin on 2017/3/2.
 */
@Component
public abstract class MeasureHost extends AbstractEntity {

  public TradingMeasureValue getMeasureValue(Measure measure, boolean isFloat, String version) {
    return measure.getValue(this, isFloat, version);
  }

  public TradingMeasureValue getMeasureValue(String measureName, boolean isFloat, String version) {
    for (Measure measure : getMeasures()) {
      if (measure.getName().equals(measureName)) {
        return measure.getValue(this, isFloat, version);
      }
    }
    return null;
  }

  public List<Measure> getMeasures() {
    return MeasureRegistry.getInstance().getMeasures(this.getType());
  }

  public TradingMeasureValue calculate(Measure mesaure, boolean isFloat, String version) {
    TradingMeasureValue measureValue = mesaure.calculate(getId(), isFloat, version);
    return measureValue;
  }

  public abstract MeasureHostType getType();

  public abstract String getName();

}

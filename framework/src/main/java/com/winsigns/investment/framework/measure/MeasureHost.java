package com.winsigns.investment.framework.measure;

import java.util.List;

import org.springframework.stereotype.Component;

import com.winsigns.investment.framework.model.AbstractEntity;

/**
 * Created by colin on 2017/3/2.
 */
@Component
public abstract class MeasureHost extends AbstractEntity implements IMeasureHost {

  @Override
  public TradingMeasureValue getMeasureValue(IMeasure measure, boolean isFloat, String version) {
    return measure.getValue(this, isFloat, version);
  }

  @Override
  public TradingMeasureValue getMeasureValue(String measureName, boolean isFloat, String version) {

    for (IMeasure measure : getMeasures()) {
      if (measure instanceof Measure) {
        if (((Measure) measure).getName().equals(measureName)) {
          return measure.getValue(this, isFloat, version);
        }
      }
    }
    return null;
  }

  @Override
  public List<IMeasure> getMeasures() {
    return MeasureRegistry.getInstance().getMeasures(this.getType());
  }

  public TradingMeasureValue calculate(IMeasure mesaure, boolean isFloat, String version) {
    TradingMeasureValue measureValue = mesaure.calculate(getId(), isFloat, version);
    return measureValue;
  }

}

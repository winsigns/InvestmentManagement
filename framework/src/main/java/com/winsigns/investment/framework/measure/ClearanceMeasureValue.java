package com.winsigns.investment.framework.measure;

/**
 * Created by colin on 2017/3/3.
 */
public class ClearanceMeasureValue extends MeasureValue {

  private int offsetDays;

  public ClearanceMeasureValue(MeasureHost measureHost, Measure measure, Double value,
      int offsetDays) {
    super(measureHost, measure, value);
    this.offsetDays = offsetDays;
  }

  public int getOffsetDays() {
    return offsetDays;
  }

  public void setOffsetDays(int offsetDays) {
    this.offsetDays = offsetDays;
  }

  @Override
  public String getKey() {
    return getMeasureHost().getType().getName() + ":" + getMeasureHost().getId() + ":"
        + getMeasure().getName() + ":" + offsetDays;
  }

  @Override
  public String getIndex() {
    return getMeasureHost().getType().getName() + ":" + getMeasureHost().getId() + ":"
        + getMeasure().getName() + ":index";
  }
}

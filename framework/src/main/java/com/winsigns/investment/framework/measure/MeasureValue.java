package com.winsigns.investment.framework.measure;

import lombok.AllArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
public abstract class MeasureValue implements IMeasureValue {

  @Setter
  private MeasureHost measureHost;

  @Setter
  private Measure measure;

  @Setter
  private Double value;

  @Override
  public MeasureHost getMeasureHost() {
    return measureHost;
  }

  @Override
  public Measure getMeasure() {
    return measure;
  }

  @Override
  public Double getValue() {
    return this.value;
  }

}

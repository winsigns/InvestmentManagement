package com.winsigns.investment.framework.measure;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class MeasureValue {

  private MeasureHost measureHost;

  private Measure measure;

  private Double value;

  public abstract String getKey();

  public abstract String getIndex();
}

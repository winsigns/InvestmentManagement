package com.winsigns.investment.framework.measure;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class MeasureValue {

  @JsonIgnore
  private MeasureHost measureHost;

  @JsonIgnore
  private Measure measure;

  private double value;

}

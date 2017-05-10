package com.winsigns.investment.inventoryService.model;

import java.util.List;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.winsigns.investment.framework.measure.MeasureHost;

@Entity
public class FloatCapitalSerial extends CapitalSerial {

  @Override
  protected List<MeasureHost> doOperator() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  @JsonIgnore
  public boolean isAffectedFloatMeasure() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  @JsonIgnore
  public boolean isAffectedNomalMeasure() {
    // TODO Auto-generated method stub
    return false;
  }

}

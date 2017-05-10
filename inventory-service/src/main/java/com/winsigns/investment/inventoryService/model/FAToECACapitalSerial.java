package com.winsigns.investment.inventoryService.model;

import java.util.List;

import javax.persistence.Entity;

import com.winsigns.investment.framework.measure.MeasureHost;

@Entity
public class FAToECACapitalSerial extends CapitalSerial {


  @Override
  protected List<MeasureHost> doOperator() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isAffectedFloatMeasure() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isAffectedNomalMeasure() {
    // TODO Auto-generated method stub
    return false;
  }

}

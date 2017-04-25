package com.winsigns.investment.inventoryService.model;

import java.util.List;

import javax.persistence.Entity;

import org.springframework.hateoas.core.Relation;

import com.winsigns.investment.framework.measure.MeasureHost;

@Entity
@Relation(value = "capital-serial", collectionRelation = "capital-serials")
public class FloatCapitalSerial extends CapitalSerial {

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

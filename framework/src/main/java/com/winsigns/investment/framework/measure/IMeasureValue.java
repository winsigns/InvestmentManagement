package com.winsigns.investment.framework.measure;

/**
 * Created by colin on 2017/3/3.
 */
public interface IMeasureValue {

  IMeasureHost getMeasureHost();

  IMeasure getMeasure();

  String getKey();

  String getIndex();

  Double getValue();
}

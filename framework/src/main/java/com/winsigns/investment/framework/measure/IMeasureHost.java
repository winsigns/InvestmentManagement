package com.winsigns.investment.framework.measure;

import java.util.List;

/**
 * Created by colin on 2017/3/2.
 */
public interface IMeasureHost {

  public TradingMeasureValue getMeasureValue(IMeasure measure, boolean isFloat, String version);

  public TradingMeasureValue getMeasureValue(String measureName, boolean isFloat, String version);

  public List<IMeasure> getMeasures();

  String getName();

  MeasureHostType getType();
}

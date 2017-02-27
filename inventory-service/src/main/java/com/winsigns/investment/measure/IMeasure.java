package com.winsigns.investment.measure;

import com.winsigns.investment.measure.data.MeasureData;

public interface IMeasure {

  boolean needToProcessInternal();

  String getHost();

  String getName();

  String getBaseKey();

  String getKey(String version);

  String getHostOfKey(String key);

  MeasureData calc(String key, MeasureData value, boolean isInternal, MeasureData stateValue);

  String[] getDependencies();

  String[] getRealDependencies();
}

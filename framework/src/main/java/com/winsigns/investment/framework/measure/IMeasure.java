package com.winsigns.investment.framework.measure;

import java.util.List;

public interface IMeasure {

  MeasureHostType getSupportedHostType();

  // 获得上一交易日的结算数据
  ClearanceMeasureValue getLatestClearanceValue(MeasureHost measureHost);

  // 获得某一交易日的结算数据
  ClearanceMeasureValue getClearanceValue(MeasureHost measureHost, int offsetDays);

  // 计算浮动数据
  TradingMeasureValue calculate(Long measureHostId, boolean isFloat, String version);

  // 获得浮动数据
  TradingMeasureValue getValue(MeasureHost measureHost, boolean isFloat, String version);

  // 获得计算因子
  List<CalculateFactor> getCalculateFactors();
}

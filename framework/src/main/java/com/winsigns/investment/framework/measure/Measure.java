package com.winsigns.investment.framework.measure;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by colin on 2017/3/2.
 */
public abstract class Measure implements ICalculateFactor {

  @Autowired
  protected MeasureValueRepository measureValueRepository;

  public Measure() {
    register();
  }

  @Override
  public String getName() {
    return this.getSupportedHostType().getName() + "." + getMeasureName(this);
  }

  public static String getMeasureName(Measure measure) {
    return measure.getClass().getSimpleName();
  }

  private void register() {
    MeasureRegistry.getInstance().register(this.getSupportedHostType(), this);
  }

  public TradingMeasureValue getValue(MeasureHost measureHost, boolean isFloat, String version) {
    return measureValueRepository.getMeasureValue(measureHost, this, isFloat, version);
  }

  public ClearanceMeasureValue getLatestClearanceValue(MeasureHost measureHost) {
    return measureValueRepository.getLatestClearanceValue(measureHost.getName(), this.getName());
  }

  public ClearanceMeasureValue getClearanceValue(MeasureHost measureHost, int offsetDays) {
    return measureValueRepository.getClearanceValue(measureHost.getName(), this.getName(),
        offsetDays);
  }

  public TradingMeasureValue calculate(Long measureHostId, boolean isFloat, String version) {
    TradingMeasureValue measureValue = doCalculate(measureHostId, isFloat, version);
    this.measureValueRepository.save(measureValue);
    return measureValue;
  }

  protected abstract TradingMeasureValue doCalculate(Long measureHostId, boolean isFloat,
      String version);

  public abstract MeasureHostType getSupportedHostType();

  // 获得计算因子
  public abstract List<ICalculateFactor> getCalculateFactors();

}

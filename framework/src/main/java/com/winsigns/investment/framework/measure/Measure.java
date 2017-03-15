package com.winsigns.investment.framework.measure;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by colin on 2017/3/2.
 */
public abstract class Measure extends CalculateFactor implements IMeasure {

  @Autowired
  protected MeasureValueRepository measureValueRepository;

  public Measure() {
    register();
    this.setName(getMeasureName(this));
    this.setFullName(this.getSupportedHostType().getName() + "." + getMeasureName(this));
  }

  public static String getMeasureName(IMeasure measure) {
    return measure.getClass().getSimpleName();
  }

  private void register() {
    MeasureRegistry.getInstance().register(this.getSupportedHostType(), this);
  }

  @Override
  public TradingMeasureValue getValue(MeasureHost measureHost, boolean isFloat, String version) {
    return measureValueRepository.getMeasureValue(measureHost, this, isFloat, version);
  }

  @Override
  public ClearanceMeasureValue getLatestClearanceValue(MeasureHost measureHost) {
    return measureValueRepository.getLatestClearanceValue(measureHost.getName(), this.getName());
  }

  @Override
  public ClearanceMeasureValue getClearanceValue(MeasureHost measureHost, int offsetDays) {
    return measureValueRepository.getClearanceValue(measureHost.getName(), this.getName(),
        offsetDays);
  }

  @Override
  public TradingMeasureValue calculate(Long measureHostId, boolean isFloat, String version) {
    TradingMeasureValue measureValue = doCalculate(measureHostId, isFloat, version);
    this.measureValueRepository.save(measureValue);
    return measureValue;
  }

  protected abstract TradingMeasureValue doCalculate(Long measureHostId, boolean isFloat,
      String version);

}

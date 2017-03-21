package com.winsigns.investment.framework.measure;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by colin on 2017/3/2.
 */
@Component
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
    // MeasureRegistry.getInstance().register(this.getSupportedHostType(), this);
    MeasureRegistry.getInstance().register(this);
  }

  /*
   * 获取指定版本的交易指标值
   */
  public TradingMeasureValue getValue(MeasureHost measureHost, boolean isFloat, String version) {
    return measureValueRepository.getMeasureValue(measureHost, this, isFloat, version);
  }

  /*
   * 获取最新版本的交易指标值
   */
  public TradingMeasureValue getValue(MeasureHost measureHost, boolean isFloat) {
    return measureValueRepository.getMeasureValue(measureHost, this, isFloat);
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

  @JsonIgnore
  public abstract MeasureHostType getSupportedHostType();

  // 获得计算因子
  @JsonIgnore
  public abstract List<ICalculateFactor> getCalculateFactors();

  public boolean isConcerned(String operatorEntityName) {
    for (ICalculateFactor operatorEntity : getCalculateFactors()) {
      if (operatorEntity.getClass().getSimpleName().equals(operatorEntityName)) {
        return true;
      }
    }
    return false;
  }

}

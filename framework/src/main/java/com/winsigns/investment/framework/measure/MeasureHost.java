package com.winsigns.investment.framework.measure;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.winsigns.investment.framework.model.AbstractEntity;
import com.winsigns.investment.framework.spring.SpringManager;

/**
 * 指标宿主
 * <p>
 * 用来承载指标
 * 
 * @author Created by colin on 2017/3/2.
 * @author yimingjin
 * 
 * @since 0.0.2
 *
 */
public abstract class MeasureHost extends AbstractEntity {

  /**
   * 由子类定义该宿主属于的类型
   * 
   * @return 宿主类型
   */
  protected abstract Class<? extends MeasureHostType> defineType();

  /**
   * 获得该宿主类型实例
   * 
   * @return 获得该宿主类型的实例
   */
  @JsonIgnore
  public MeasureHostType getHostType() {
    return SpringManager.getApplicationContext().getBean(defineType());
  }

  /**
   * 该宿主的父节点类型
   * 
   * @return 宿主的父节点的类型
   */
  @JsonIgnore
  public abstract MeasureHost parent();

  /**
   * 获取该宿主的特定交易指标值
   * 
   * @param measure 指标
   * @param isFloat 是否浮动
   * @param version 版本
   * @return
   */
  public TradingMeasureValue getMeasureValue(Measure measure, boolean isFloat, String version) {
    return measure.getValue(this, isFloat, version);
  }

  /**
   * 获取该宿主的特定交易指标值
   * 
   * @param measureName 指标名
   * @param isFloat 是否浮动
   * @param version 版本
   * @return
   */
  public TradingMeasureValue getMeasureValue(String measureName, boolean isFloat, String version) {
    for (Measure measure : getMeasures()) {
      if (measure.getName().equals(measureName)) {
        return measure.getValue(this, isFloat, version);
      }
    }
    return null;
  }

  /**
   * 获取该宿主的所有指标
   * 
   * @return
   */
  @JsonIgnore
  public List<Measure> getMeasures() {
    return SpringManager.getApplicationContext().getBean(MeasureRepository.class)
        .getMeasures(this.getHostType());
  }

  /**
   * 获取该宿主的所有最新指标值
   * 
   * @return
   */
  @JsonIgnore
  public List<TradingMeasureValue> getMeasureValues() {
    List<TradingMeasureValue> measureValues = new ArrayList<TradingMeasureValue>();

    List<Measure> measures = getMeasures();
    for (Measure measure : measures) {
      TradingMeasureValue measureValue = measure.getValue(this, false);
      measureValues.add(measureValue);
    }
    return measureValues;
  }
}

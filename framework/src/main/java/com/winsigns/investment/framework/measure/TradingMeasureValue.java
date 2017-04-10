package com.winsigns.investment.framework.measure;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

/**
 * 交易指标值
 * 
 * <p>
 * 交易指标区分是否为浮动指标和版本
 * 
 * @author Created by colin on 2017/3/3
 * @since 0.0.2
 */
public class TradingMeasureValue extends MeasureValue {

  @Setter
  @Getter
  @JsonIgnore
  private boolean isFloat;

  @Setter
  @Getter
  @JsonIgnore
  private String version;

  /**
   * 构造一个交易指标值
   * <p>
   * 该值是完整的
   * 
   * @param measureHost 指标宿主
   * @param measure 指标
   * @param isFloat 是否浮动指标
   * @param version 版本
   * @param value 具体值
   */
  public TradingMeasureValue(MeasureHost measureHost, Measure measure, boolean isFloat,
      String version, double value) {
    super(measureHost, measure, value, false);
    this.version = version;
    this.isFloat = isFloat;
  }

  /**
   * 构造一个交易指标值
   * <p>
   * 该值的具体值初始化为0.0
   * 
   * @param measureHost 指标宿主
   * @param measure 指标
   * @param isFloat 是否浮动指标
   * @param version 版本
   */
  public TradingMeasureValue(MeasureHost measureHost, Measure measure, boolean isFloat,
      String version) {
    this(measureHost, measure, isFloat, version, 0.0);
  }

  /**
   * 构造一个交易指标值
   * <p>
   * 该值的具体值初始化为0.0<br>
   * 该值的版本为null
   * 
   * @param measureHost 指标宿主
   * @param measure 指标
   * @param isFloat 是否浮动指标
   */
  public TradingMeasureValue(MeasureHost measureHost, Measure measure, boolean isFloat) {
    this(measureHost, measure, isFloat, null, 0.0);
  }
}

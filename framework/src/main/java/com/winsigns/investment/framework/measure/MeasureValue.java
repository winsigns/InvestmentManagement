package com.winsigns.investment.framework.measure;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 具体指标的值的抽象基类
 * 
 * <p>
 * 一个指标值有一个宿主，一个指标，一个具体的值
 * 
 * @author Created by colin on 2017/3/3.
 * @since 0.0.2
 */
@Data
@AllArgsConstructor
public abstract class MeasureValue {

  @JsonIgnore
  private MeasureHost measureHost;

  @JsonIgnore
  private Measure measure;

  private double value;

  /**
   * 通过该字段判断，该值是否准备就绪
   * <p>
   * 默认为false
   */
  @JsonIgnore
  private boolean isReady;
}

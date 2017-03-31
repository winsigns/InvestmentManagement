package com.winsigns.investment.framework.measure;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 指标体系的value
 * 
 * @author yimingjin
 * @since 0.0.3
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessorValue {

  // 宿主类型
  String measureHostType;

  // 宿主ID
  Long measureHostId;

  // 指标名
  String measureName;

  // 是否浮动指标 清算指标为null
  Boolean isFloat;

  // 清算日期 交易指标为null
  String clearDate;

  // 指标具体值
  double value;

  public ProcessorValue(String measureHostName, Long measureHostId, String measureName,
      boolean isFloat) {
    this(measureHostName, measureHostId, measureName, isFloat, null, 0.0);
  }

  public ProcessorValue(String measureHostName, Long measureHostId, String measureName,
      boolean isFloat, double value) {
    this(measureHostName, measureHostId, measureName, isFloat, null, value);
  }

  public ProcessorValue(String measureHostName, Long measureHostId, String measureName) {
    this(measureHostName, measureHostId, measureName, null);
  }

  public ProcessorValue(String measureHostName, Long measureHostId) {
    this(measureHostName, measureHostId, null);
  }

  public ProcessorValue(String measureHostName, Long measureHostId, String measureName,
      String clearDate) {
    this(measureHostName, measureHostId, measureName, null, clearDate, 0.0);
  }

  public ProcessorValue(String measureHostName, Long measureHostId, String measureName,
      String clearDate, double value) {
    this(measureHostName, measureHostId, measureName, null, clearDate, value);
  }

}

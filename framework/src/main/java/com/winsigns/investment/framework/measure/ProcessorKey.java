package com.winsigns.investment.framework.measure;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 指标体系的key
 * 
 * @author yimingjin
 * @since 0.0.3
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessorKey {

  public final static String INIT = "stram-init";

  // 操作序号
  String operatorSequence;

  // 操作名
  String operatorName;

  // 操作影响到的宿主类型
  String affectedMeasureHostType;

  // 操作影响到的宿主id
  Long affectedMeasureHostId;

  // 是否影响普通指标
  Boolean isAffectedFloatMeasure;

  // 是否影响浮动指标
  Boolean isAffectedNomalMeasure;

  public ProcessorKey(String operatorSequence, String operatorName) {
    this(operatorSequence, operatorName, null, null, null, null);
  }
}

package com.winsigns.investment.framework.measure;

import lombok.Setter;

/**
 * 用作跨服务间的指标依赖
 * 
 * @author Created by colin on 2017/3/10.
 * @since 0.0.2
 */
public class MockMeasure implements IMeasure {

  @Setter
  private String name;

  @Setter
  private String measureHostType;

  public MockMeasure(String measureHostType, String measureName) {

    this.measureHostType = measureHostType;
    this.name = measureName;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public String getFullName() {
    return this.measureHostType + "." + this.name;
  }
}

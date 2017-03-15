package com.winsigns.investment.framework.measure;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by colin on 2017/3/10.
 */
public class MockMeasure implements ICalculateFactor {

  @Getter
  @Setter
  private MeasureHostType supportedHostType;

  @Setter
  private String name;

  public MockMeasure(String measureName, String meauserHostType) {

    this.supportedHostType = new MockMeasureHostType(meauserHostType);
    this.name = measureName;
  }

  @Override
  public String getName() {
    return this.name;
  }

}

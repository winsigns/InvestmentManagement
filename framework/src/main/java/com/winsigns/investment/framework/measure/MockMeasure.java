package com.winsigns.investment.framework.measure;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by colin on 2017/3/10.
 */
public class MockMeasure extends CalculateFactor {

  @Getter
  @Setter
  private MeasureHostType supportedHostType;

  public MockMeasure(String measureName, String meauserHostType) {

    this.supportedHostType = new MockMeasureHostType(meauserHostType);
    this.setName(measureName);
  }

}

package com.winsigns.investment.framework.measure;

/**
 * Created by colin on 2017/3/10.
 */
public class MockMeasureHostType extends MeasureHostType {

  private String name;

  public MockMeasureHostType(String name) {
    super();
    this.name = name;
  }

  @Override
  public String getName() {
    return this.name;
  }
}

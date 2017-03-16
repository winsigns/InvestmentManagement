package com.winsigns.investment.framework.measure;

import lombok.Setter;

/**
 * Created by colin on 2017/3/10.
 */
public class MockCalculateFactor implements ICalculateFactor {

  @Setter
  private String name;

  public MockCalculateFactor(String measureName) {

    this.name = measureName;
  }

  @Override
  public String getName() {
    return this.name;
  }

}

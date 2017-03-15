package com.winsigns.investment.framework.measure;

/**
 * Created by colin on 2017/3/6.
 */
public class MeasureHostType {

  public String getName() {
    return this.getClass().getSimpleName();
  }

  @Override
  public int hashCode() {
    return getName().hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof MeasureHostType
        && this.getName().equals(((MeasureHostType) obj).getName());
  }

}

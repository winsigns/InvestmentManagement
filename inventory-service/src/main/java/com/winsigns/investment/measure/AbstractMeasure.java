package com.winsigns.investment.measure;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMeasure implements com.winsigns.investment.measure.IMeasure {

  protected List<String> dependencies = new ArrayList<String>();

  abstract public void createDependencies();

  public AbstractMeasure() {
    //所有Measure都会依赖时序
    dependencies.add("timer.sequence");
    createDependencies();
  }

  @Override
  public boolean needToProcessInternal() {
    //False is the default behavior
    return false;
  }

  @Override
  public String getBaseKey() {
    return String.format("%s.%s", getHost(), getName());
  }

  @Override
  public String getKey(String version) {
    return String.format("%s.%s.%s", getHost(), getName(), version);
  }

  @Override
  public String getHostOfKey(String key) {
    String[] temp = key.split("\\.");
    return temp[0];
  }

  @Override
  public String[] getDependencies() {
    return dependencies.toArray(new String[0]);
  }

  @Override
  public String[] getRealDependencies() {
    List<String> result = new ArrayList<String>();
    for (String dep : dependencies) {
      if (!getHost().equals(getHostOfKey(dep))) {
        result.add(String.format("%s.source", dep));
      } else {
        result.add(String.format("%s.processor", dep));
      }
    }
    return result.toArray(new String[0]);
  }
}

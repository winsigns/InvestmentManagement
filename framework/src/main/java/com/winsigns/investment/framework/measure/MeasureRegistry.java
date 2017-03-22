package com.winsigns.investment.framework.measure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by colin on 2017/3/2.
 */

public class MeasureRegistry {

  private static MeasureRegistry instance = new MeasureRegistry();

  public static MeasureRegistry getInstance() {
    return instance;
  }

  private MeasureRegistry() {
    this.measureRegistry = new HashMap<MeasureHostType, List<Measure>>();
    this.measures = new ArrayList<Measure>();
  }

  // 因MeasureHostType是一个bean，因此不能在构造函数中获得，增加另一个存储结构
  private List<Measure> measures;

  public void register(Measure measure) {
    measures.add(measure);
  }

  public void rehash() {
    measures.forEach(measure -> register(measure.getSupportedHostType(), measure));
  }

  private HashMap<MeasureHostType, List<Measure>> measureRegistry;

  public boolean contains(MeasureHostType measureHostType) {
    return this.measureRegistry.containsKey(measureHostType);
  }

  public boolean contains(String measureName) {

    for (List<Measure> measures : this.measureRegistry.values()) {
      for (Measure measure : measures) {
        if (measure.getName().equals(measureName)) {
          return true;
        }
      }
    }
    return false;
  }

  public void register(MeasureHostType measureHostType, Measure measure) {
    if (!this.measureRegistry.containsKey(measureHostType)) {
      this.measureRegistry.put(measureHostType, new ArrayList<Measure>());
    }

    this.measureRegistry.get(measureHostType).add(measure);
  }

  public List<Measure> getMeasures(MeasureHostType measureHostType) {
    return this.measureRegistry.get(measureHostType);
  }

  public List<Measure> getMeasures() {
    ArrayList<Measure> measures = new ArrayList<Measure>();
    for (List<Measure> measure : this.measureRegistry.values()) {
      measures.addAll(measure);
    }
    return measures;
  }

  public Measure getMeasure(MeasureHostType measureHostType, String measureName) {
    for (Measure measure : this.getMeasures(measureHostType)) {
      if (((Measure) measure).getName().equals(measureName)) {
        return measure;
      }
    }
    return null;
  }
}

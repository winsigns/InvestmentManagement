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
  }

  private HashMap<MeasureHostType, List<Measure>> measureRegistry;

  public boolean contains(MeasureHostType measureHostType) {
    return this.measureRegistry.containsKey(measureHostType);
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

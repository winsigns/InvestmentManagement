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
    this.measureRegistry = new HashMap<MeasureHostType, List<IMeasure>>();
  }

  private HashMap<MeasureHostType, List<IMeasure>> measureRegistry;

  public boolean contains(MeasureHostType measureHostType) {
    return this.measureRegistry.containsKey(measureHostType);
  }

  public void register(MeasureHostType measureHostType, IMeasure measure) {
    if (!this.measureRegistry.containsKey(measureHostType)) {
      this.measureRegistry.put(measureHostType, new ArrayList<IMeasure>());
    }

    this.measureRegistry.get(measureHostType).add(measure);
  }

  public List<IMeasure> getMeasures(MeasureHostType measureHostType) {
    return this.measureRegistry.get(measureHostType);
  }

  public List<IMeasure> getMeasures() {
    ArrayList<IMeasure> measures = new ArrayList<IMeasure>();
    for (List<IMeasure> measure : this.measureRegistry.values()) {
      measures.addAll(measure);
    }
    return measures;
  }

  public IMeasure getMeasure(MeasureHostType measureHostType, String measureName) {
    for (IMeasure measure : this.getMeasures(measureHostType)) {
      if (measure instanceof Measure)
        if (((Measure) measure).getName().equals(measureName)) {
          return measure;
        }
    }
    return null;
  }
}

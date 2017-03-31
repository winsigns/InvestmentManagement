package com.winsigns.investment.framework.measure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

/**
 * 指标仓库
 * 
 * <p>
 * 作为一个bean存储所有本地指标
 * 
 * @author Created by colin on 2017/3/2.
 * @author yimingjin
 * @since 0.0.2
 */
@Component
public class MeasureRepository {

  /**
   * 存放指标的map
   */
  HashMap<MeasureHostType, List<Measure>> repository =
      new HashMap<MeasureHostType, List<Measure>>();

  /**
   * 注册指标
   * 
   * @param measure
   */
  public void register(Measure measure) {
    if (!this.repository.containsKey(measure.getSupportedHostType())) {
      repository.put(measure.getSupportedHostType(), new ArrayList<Measure>());
    }
    this.repository.get(measure.getSupportedHostType()).add(measure);
  }

  @Deprecated
  public void register(MeasureHostType measureHostType, Measure measure) {
    if (!this.repository.containsKey(measureHostType)) {
      this.repository.put(measureHostType, new ArrayList<Measure>());
    }

    this.repository.get(measureHostType).add(measure);
  }

  /**
   * 判断是否包含指定MHT的指标
   * 
   * @param measureHostType 指定的MHT
   * @return 是否包含
   */
  public boolean contains(MeasureHostType measureHostType) {
    return this.repository.containsKey(measureHostType);
  }

  /**
   * 获取所有的MHT
   * 
   * @return MHT的集合
   */
  public Set<MeasureHostType> getMeasureHostTypes() {
    return this.repository.keySet();
  }

  public MeasureHostType getMeasureHostType(String name) {
    for (MeasureHostType mht : getMeasureHostTypes()) {
      if (mht.getName().equals(name)) {
        return mht;
      }
    }
    return null;
  }

  /**
   * 判断是否包含指定的指标
   * 
   * @param measureName 指定的指标名
   * @return 是否包含
   */
  public boolean contains(String measureName) {
    for (List<Measure> measures : this.repository.values()) {
      for (Measure measure : measures) {
        if (measure.getName().equals(measureName)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * 获取指定MHT的所有指标列表
   * 
   * @param measureHostType
   * @return 指标列表
   */
  public List<Measure> getMeasures(MeasureHostType measureHostType) {
    return this.repository.get(measureHostType);
  }

  /**
   * 获取指标库的所有指标
   * 
   * @return 指标列表
   */
  public List<Measure> getMeasures() {
    ArrayList<Measure> measures = new ArrayList<Measure>();
    for (List<Measure> measure : this.repository.values()) {
      measures.addAll(measure);
    }
    return measures;
  }

  /**
   * 获取指定MHT下的指定指标
   * 
   * @param measureHostType 指标类型
   * @param measureName 指标名
   * @return 指定指标
   */
  public Measure getMeasure(MeasureHostType measureHostType, String measureName) {
    for (Measure measure : this.getMeasures(measureHostType)) {
      if (((Measure) measure).getName().equals(measureName)) {
        return measure;
      }
    }
    return null;
  }
}

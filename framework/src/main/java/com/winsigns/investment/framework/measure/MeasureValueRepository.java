package com.winsigns.investment.framework.measure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by colin on 2017/3/2.
 */

@Component
public class MeasureValueRepository {

  @Autowired
  RedisTemplate<String, Double> measureRepository;

  @Autowired
  StringRedisTemplate indexRepository;

  public TradingMeasureValue getMeasureValue(MeasureHost measureHost, Measure measure,
      boolean isFloat, String version) {

    String key = measureHost.getType().getName() + ":" + measureHost.getId() + ":"
        + measure.getName() + ":" + isFloat + ":" + version;

    Double value = measureRepository.opsForValue().get(key);

    return new TradingMeasureValue(measureHost, measure, isFloat, version, value);
  }

  public TradingMeasureValue getMeasureValue(MeasureHost measureHost, Measure measure,
      boolean isFloat) {

    String lasterKey = measureHost.getType().getName() + ":" + measureHost.getId() + ":"
        + measure.getName() + ":" + isFloat + ":latest";
    String latestVersion = indexRepository.opsForValue().get(lasterKey);

    if (latestVersion == null) {
      return null;
    }

    String key = measureHost.getType().getName() + ":" + measureHost.getId() + ":"
        + measure.getName() + ":" + isFloat + ":" + latestVersion;

    Double value = measureRepository.opsForValue().get(key);

    return new TradingMeasureValue(measureHost, measure, isFloat, latestVersion, value);
  }

  public ClearanceMeasureValue getLatestClearanceValue(String measureHostName, String measureName) {
    return null;
  }

  public ClearanceMeasureValue getClearanceValue(String measureHostName, String measureName,
      int offsetDays) {
    return null;
  }

  public void save(TradingMeasureValue value) {
    Double tmp = value.getValue();
    if (tmp == null) {
      tmp = 0.0;
    }
    measureRepository.opsForValue().set(getKey(value), value.getValue());

    // 更新索引，索引存放该类指标的最新版本
    String key = getKey(value);
    int idx = key.lastIndexOf(":");
    String latestKey = key.substring(0, idx) + ":latest";
    indexRepository.opsForValue().set(latestKey, value.getVersion());
  }

  public void save(ClearanceMeasureValue value) {
    measureRepository.opsForValue().set(getKey(value), value.getValue());
  }

  protected String getKeyBase(MeasureValue value) {
    return value.getMeasureHost().getType().getName() + ":" + value.getMeasureHost().getId() + ":"
        + value.getMeasure().getName();
  }

  protected String getKey(TradingMeasureValue value) {
    return getKeyBase(value) + ":" + value.isFloat() + ":" + value.getVersion();
  }

  protected String getKey(ClearanceMeasureValue value) {
    return getKeyBase(value) + ":" + value.getOffsetDays();
  }
}

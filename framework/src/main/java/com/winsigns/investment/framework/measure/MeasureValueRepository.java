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
      return new TradingMeasureValue(measureHost, measure, isFloat, latestVersion, 0.0);
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

    measureRepository.opsForValue().set(value.key() + ":" + value.getVersion(), value.getValue());

    // 更新索引，索引存放该类指标的最新版本
    indexRepository.opsForValue().set(value.key() + ":latest", value.getVersion());
  }

  public void save(ClearanceMeasureValue value) {
    measureRepository.opsForValue().set(value.key(), value.getValue());
  }
}

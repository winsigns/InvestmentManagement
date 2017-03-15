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

    // 先查询索引版本，如果所需版本大于索引版本，则获取索引版本
    String index = measureHost.getType().getName() + ":" + measureHost.getId() + ":"
        + measure.getName() + ":" + isFloat + ":inedx";
    String maxVersion = indexRepository.opsForValue().get(index);
    if (version.compareTo(maxVersion) > 0)
      version = maxVersion;

    String key = measureHost.getType().getName() + ":" + measureHost.getId() + ":"
        + measure.getName() + ":" + isFloat + ":" + version;

    Double value = measureRepository.opsForValue().get(key);

    return new TradingMeasureValue(measureHost, measure, isFloat, version, value);
  }

  public ClearanceMeasureValue getLatestClearanceValue(String measureHostName, String measureName) {
    return null;
  }

  public ClearanceMeasureValue getClearanceValue(String measureHostName, String measureName,
      int offsetDays) {
    return null;
  }

  public void save(MeasureValue value) {

    measureRepository.opsForValue().set(value.getKey(), value.getValue());

    // 更新索引，索引存放该类指标的最新版本
    int idx = value.getKey().lastIndexOf(":");
    String index = value.getKey().substring(idx + 1, value.getKey().length());
    indexRepository.opsForValue().set(value.getIndex(), index);
  }
}

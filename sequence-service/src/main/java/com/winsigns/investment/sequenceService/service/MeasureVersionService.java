package com.winsigns.investment.sequenceService.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class MeasureVersionService {

  Logger log = LoggerFactory.getLogger(MeasureVersionService.class);

  @Autowired
  private RedisTemplate<String, Integer> redisTemplate;

  private static final String STR_MEARSURE_VERSION_FIX = "measure-version:";

  /*
   * 获取一个指标版本
   */
  public String getMeasureVersion() {
    String key = STR_MEARSURE_VERSION_FIX + getDate();
    Integer nowVersion = redisTemplate.opsForValue().get(key);
    if (nowVersion == null) {
      nowVersion = 0;
    } else {
      nowVersion++;
    }
    redisTemplate.opsForValue().set(key, nowVersion);
    return formatVersion(nowVersion);
  }

  private String getDate() {
    SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");// 设置日期格式
    return df.format(new Date());
  }

  private String formatVersion(Integer nowVersion) {
    return String.format("%s%014d", getDate(), nowVersion);
  }

}

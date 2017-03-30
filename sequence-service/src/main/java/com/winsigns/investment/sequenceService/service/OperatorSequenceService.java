package com.winsigns.investment.sequenceService.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class OperatorSequenceService {

  Logger log = LoggerFactory.getLogger(OperatorSequenceService.class);

  @Autowired
  private StringRedisTemplate redisTemplate;

  private static final String STR_OPERATOR_SEQUENCE_FIX = "operator-sequence:";

  /**
   * 获取一个操作序号
   * 
   * @param date 指定日期，空则取系统日期
   * @return 序列化后的操作序号
   */
  public synchronized String getOperatorSequence(String date) {
    String nowDate = date == null ? new SimpleDateFormat("yyyyMMdd").format(new Date()) : date;
    String key =
        date == null ? STR_OPERATOR_SEQUENCE_FIX + nowDate : STR_OPERATOR_SEQUENCE_FIX + date;

    Long currentSequence = redisTemplate.opsForValue().increment(key, 1L);
    return formatSequence(nowDate, currentSequence);

  }

  private String formatSequence(String date, Long nowVersion) {
    return String.format("%s%012d", date, nowVersion);
  }

}

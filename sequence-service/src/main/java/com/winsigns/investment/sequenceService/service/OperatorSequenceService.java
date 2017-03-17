package com.winsigns.investment.sequenceService.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class OperatorSequenceService {

  Logger log = LoggerFactory.getLogger(OperatorSequenceService.class);

  @Autowired
  private RedisTemplate<String, Integer> redisTemplate;

  private static final String STR_OPERATOR_SEQUENCE_FIX = "operator-sequence:";

  /*
   * 每次从redis获取10000个序列
   */
  private static final Integer INTERVAL = 10000;

  private Integer currentSequence = 0;

  private Integer surplusSequence = 0;

  private String nowDate = new SimpleDateFormat("yyyyMMdd").format(new Date());

  /*
   * 获取一个指标版本
   */
  public synchronized String getOperatorSequence() {
    if (isSameDay()) {
      String key = STR_OPERATOR_SEQUENCE_FIX + nowDate;
      if (!isEnough()) {
        currentSequence = redisTemplate.opsForValue().get(key);
        if (currentSequence == null)
          currentSequence = 0;
        incSequence();
        redisTemplate.opsForValue().set(key, currentSequence + surplusSequence);
      }
    }
    // 如果不是同一天，则需要从0开始
    else {
      nowDate = getDate();
      initSequence();
      return getOperatorSequence();
    }
    String sequence = formatSequence(currentSequence++);
    surplusSequence--;
    return sequence;
  }

  private String getDate() {
    SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");// 设置日期格式
    return df.format(new Date());
  }

  private String formatSequence(Integer nowVersion) {
    return String.format("%s%012d", getDate(), nowVersion);
  }

  private boolean isEnough() {
    if (surplusSequence == 0) {
      return false;
    }
    return true;
  }

  private void initSequence() {
    currentSequence = 0;
    surplusSequence = 0;
  }

  private void incSequence() {
    surplusSequence = surplusSequence + INTERVAL;
  }

  private boolean isSameDay() {
    return nowDate.equals(getDate());
  }

}

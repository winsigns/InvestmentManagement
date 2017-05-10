package com.winsigns.investment.generalService.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.winsigns.investment.generalService.model.OperatorSequence;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OperatorSequenceService {

  @Autowired
  private StringRedisTemplate redisTemplate;

  private static final String STR_OPERATOR_SEQUENCE_FIX = "operator-sequence:";

  /**
   * 获取一个操作序号
   * 
   * @return 序列化后的操作序号
   */
  public synchronized OperatorSequence getOperatorSequence() {
    String nowDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
    String key = STR_OPERATOR_SEQUENCE_FIX + nowDate;

    Long currentSequence = redisTemplate.opsForValue().increment(key, 1L);
    OperatorSequence operatorSequence = OperatorSequence.formatSequence(nowDate, currentSequence);
    log.info(operatorSequence.toString());
    return operatorSequence;

  }

}

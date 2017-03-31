package com.winsigns.investment.framework.measure;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Component;

import com.winsigns.investment.framework.model.OperatorEntity;

/**
 * 指标宿主类型的基类
 * <p>
 * 宿主类型是一个bean<br>
 * 子类需要集成该类，并通过注解@KafkaListener定义接收消息
 * 
 * @author Created by colin on 2017/3/6.
 * @since 0.0.2
 */
@Component
public abstract class MeasureHostType {

  final static JsonDeserializer<ProcessorKey> keyDeserializer =
      new JsonDeserializer<ProcessorKey>(ProcessorKey.class);
  final static JsonDeserializer<ProcessorValue> valueDeserializer =
      new JsonDeserializer<ProcessorValue>(ProcessorValue.class);

  @Autowired
  KafkaTemplate<ProcessorKey, ProcessorValue> kafkaTemplate;

  @Autowired
  MeasureRepository measureRepository;

  /**
   * 获取该指标类型的名
   * 
   * @return 返回指标类型名
   */
  public String getName() {
    return this.getClass().getSimpleName();
  }

  /**
   * 获取该宿主类型关心的操作
   * 
   * @return 操作类型
   */
  public abstract Class<? extends OperatorEntity> getConcernedOperator();

  /**
   * 获取该宿主类型使用的宿主仓库
   * 
   * @return 使用的宿主仓库
   */
  protected abstract JpaRepository<? extends MeasureHost, Long> getRepository();

  /**
   * 子类需要为其增加 @KafkaListener(topics = {""}, group = "")
   * 
   * @param record
   * 
   * @since 0.0.3
   */
  public abstract void listen(ConsumerRecord<String, String> record);

  /**
   * 为子类提供的监听方法
   * 
   * @param key 保存operator-sequence
   * @param value 保存OperatorInfo
   * 
   * @since 0.0.3
   */
  protected void onListen(ConsumerRecord<String, String> record) {

    ProcessorKey key = keyDeserializer.deserialize("", record.key().getBytes());

    if (isConcernedOperator(key.getOperatorName())) {
      for (Measure measure : measureRepository.getMeasures(this)) {
        if (measure.isConcerned(key.getOperatorName())) {
          // 遍历所有的宿主
          for (MeasureHost measureHost : getRepository().findAll()) {

            if (key.getIsAffectedFloatMeasure()) {
              send(key, measureHost, measure, true);
            }

            if (key.getIsAffectedNomalMeasure()) {
              send(key, measureHost, measure, false);
            }
          }
        }
      }
    }
  }

  /**
   * @param type 操作类型
   * @return 是否关心的操作
   * @since 0.0.3
   */
  boolean isConcernedOperator(String type) {
    if (getConcernedOperator() == null) {
      return false;
    }
    return getConcernedOperator().getSimpleName().equals(type);
  }

  /**
   * 向kafka发送消息
   * 
   * @param operatorSequence 操作序号
   * @param operatorName 操作名
   * @param measureHost 指标宿主
   * @param measure 指标
   * @param isFloat 是否浮动
   * 
   * @since 0.0.3
   */
  void send(ProcessorKey key, MeasureHost measureHost, Measure measure, boolean isFloat) {
    ProcessorValue value = new ProcessorValue(measureHost.getHostType().getName(),
        measureHost.getId(), measure.getName(), isFloat);

    kafkaTemplate.send(measure.getFullName() + "." + key.getOperatorName(), key, value);
  }
}

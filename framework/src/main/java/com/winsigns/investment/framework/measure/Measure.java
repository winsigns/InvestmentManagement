package com.winsigns.investment.framework.measure;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.winsigns.investment.framework.measure.kafkaStreams.JsonSerializer;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by colin on 2017/3/2.
 */
@Component
public abstract class Measure implements ICalculateFactor {

  private Logger log = LoggerFactory.getLogger(Measure.class);

  static private String MEASURE_TOPIC = "measures";

  JsonSerializer<MessageKey> keyserializer = JsonSerializer.defaultConfig(MessageKey.class);

  @Autowired
  protected MeasureValueRepository measureValueRepository;

  @Autowired
  protected KafkaTemplate<Object, Object> kafkaTemplate;

  public Measure() {
    register();
  }

  @Override
  public String getName() {
    return this.getSupportedHostType().getName() + "." + getMeasureName(this);
  }

  public static String getMeasureName(Measure measure) {
    return measure.getClass().getSimpleName();
  }

  private void register() {
    // MeasureRegistry.getInstance().register(this.getSupportedHostType(), this);
    MeasureRegistry.getInstance().register(this);
  }

  /*
   * 获取指定版本的交易指标值
   */
  public TradingMeasureValue getValue(MeasureHost measureHost, boolean isFloat, String version) {
    return measureValueRepository.getMeasureValue(measureHost, this, isFloat, version);
  }

  /*
   * 获取最新版本的交易指标值
   */
  public TradingMeasureValue getValue(MeasureHost measureHost, boolean isFloat) {
    return measureValueRepository.getMeasureValue(measureHost, this, isFloat);
  }

  public ClearanceMeasureValue getLatestClearanceValue(MeasureHost measureHost) {
    return measureValueRepository.getLatestClearanceValue(measureHost.getName(), this.getName());
  }

  public ClearanceMeasureValue getClearanceValue(MeasureHost measureHost, int offsetDays) {
    return measureValueRepository.getClearanceValue(measureHost.getName(), this.getName(),
        offsetDays);
  }

  @Data
  @AllArgsConstructor
  protected static class MessageKey {
    String measureHostType;
    Long measureHostId;
    String measureName;
    boolean isFlost;
    String version;
  }

  public TradingMeasureValue calculate(Long measureHostId, boolean isFloat, String version) {

    TradingMeasureValue measureValue = doCalculate(measureHostId, isFloat, version);
    this.measureValueRepository.save(measureValue);

    // 保存之后发送一个消息
    log.info(measureValue.key());
    kafkaTemplate.send(MEASURE_TOPIC, measureValue.key(), String.valueOf(measureValue.getValue()));

    return measureValue;
  }

  protected abstract TradingMeasureValue doCalculate(Long measureHostId, boolean isFloat,
      String version);

  @JsonIgnore
  public abstract MeasureHostType getSupportedHostType();

  // 获得计算因子
  @JsonIgnore
  public abstract List<ICalculateFactor> getCalculateFactors();

  public boolean isConcerned(String operatorEntityName) {
    for (ICalculateFactor operatorEntity : getCalculateFactors()) {
      if (operatorEntity.getClass().getSimpleName().equals(operatorEntityName)) {
        return true;
      }
    }
    return false;
  }

}

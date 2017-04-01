package com.winsigns.investment.framework.measure;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.winsigns.investment.framework.model.OperatorEntity;

/**
 * 所有指标的基类
 * <p>
 * 指标是一个bean，受spring管理，应该用autowired注入
 * 
 * @author Created by colin on 2017/3/2.
 * @author yimingjin
 * @since 0.0.2
 *
 */
@Component
public abstract class Measure implements IMeasure {

  private Logger log = LoggerFactory.getLogger(Measure.class);

  static private String MEASURE_TOPIC = "measures";

  @Autowired
  protected MeasureRepository measureRepository;

  @Autowired
  protected MeasureValueRepository measureValueRepository;

  @Autowired
  protected KafkaTemplate<ProcessorKey, ProcessorValue> kafkaTemplate;

  /**
   * 获取指标的全名
   * 
   * @return 指标的全名 类型.名字
   */
  @Override
  public String getFullName() {
    return this.getSupportedHostType().getName() + "." + this.getName();
  }

  /**
   * 获取指标的名字
   * 
   * @return 指标名
   */
  @Override
  public String getName() {
    return this.getClass().getSimpleName();
  }

  /**
   * 由子类定义该指标属于哪一类型
   * 
   * @return 返回指标的宿主类型
   */
  @JsonIgnore
  public abstract MeasureHostType getSupportedHostType();

  /**
   * 由子类定义所关心的操作，底层指标才需要定义
   * 
   * @return 关心的操作
   */
  @JsonIgnore
  public abstract List<Class<? extends OperatorEntity>> getConcernedOperator();

  /**
   * 由子类定义指标的依赖关系
   * 
   * @return 依赖的下级指标
   */
  @JsonIgnore
  public abstract List<IMeasure> getDependentMeasure();

  /**
   * 判断该操作是否为指标关心的操作
   * 
   * @param operatorName 操作名
   * @return 是否关心
   */
  private boolean isConcerned(String operatorName) {
    for (Class<? extends OperatorEntity> clazz : getConcernedOperator()) {
      if (clazz.getSimpleName().equals(operatorName)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 获取指定版本的交易指标值
   * 
   * @param measureHost 宿主
   * @param isFloat 是否浮动
   * @param version 版本
   * @return 返回交易指标值的一个实例
   */
  public TradingMeasureValue getValue(MeasureHost measureHost, boolean isFloat, String version) {
    return measureValueRepository.getMeasureValue(measureHost, this, isFloat, version);
  }

  /**
   * 获取最新的特定交易指标
   * 
   * @param measureHost 宿主
   * @param isFloat 是否浮动
   * @return 返回交易指标值的一个实例，其版本是最新的
   */
  public TradingMeasureValue getValue(MeasureHost measureHost, boolean isFloat) {
    return measureValueRepository.getMeasureValue(measureHost, this, isFloat);
  }

  /**
   * 获取最新的清算指标
   * 
   * @param measureHost 宿主
   * @return 返回最新的清算指标值的一个实例
   */
  public ClearanceMeasureValue getLatestClearanceValue(MeasureHost measureHost) {
    return measureValueRepository.getLatestClearanceValue(measureHost, this);
  }

  /**
   * 获取指定偏移的清算指标
   * 
   * @param measureHost 宿主
   * @return 返回指定偏移的清算指标值的一个实例
   */
  public ClearanceMeasureValue getClearanceValue(MeasureHost measureHost, int offsetDays) {
    return measureValueRepository.getClearanceValue(measureHost, this, offsetDays);
  }

  /**
   * 交易指标的计算
   * 
   * @param key
   * @param value
   * @return
   */
  public MeasureValue calculate(ProcessorKey key, ProcessorValue value) {

    // 操作影响的宿主类型
    MeasureHostType affectedMHT =
        measureRepository.getMeasureHostType(key.getAffectedMeasureHostType());

    // 操作计算的宿主
    MeasureHost affectedHost = affectedMHT.getRepository().findOne(key.getAffectedMeasureHostId());

    // 当前计算的宿主类型
    MeasureHostType currentMHT = this.getSupportedHostType();

    // 当前计算的宿主
    MeasureHost currentHost = currentMHT.getRepository().findOne(value.getMeasureHostId());

    if (value.getIsFloat() != null) { // 交易指标
      TradingMeasureValue measureValue = null;

      if (!this.isConcerned(key.getOperatorName())) { // 如果操作不是该指标关心的，那么直接更新
        measureValue =
            new TradingMeasureValue(currentHost, this, value.isFloat, key.getOperatorSequence());
        this.measureValueRepository.saveNoChange(measureValue);
      } else {
        if (affectedMHT == currentMHT) { // 由操作触发的指标或者与其类型相同的

          if (key.getAffectedMeasureHostId() == value.getMeasureHostId()) {

            measureValue = doCalculate(currentHost, value.getIsFloat(), key.getOperatorSequence());
            this.measureValueRepository.save(measureValue);
            measureValue.setReady(true);
          } else { // 因为当前的宿主id与操作影响的id不同，因此可以直接写入值
            measureValue = new TradingMeasureValue(currentHost, this, value.isFloat,
                key.getOperatorSequence());
            this.measureValueRepository.saveNoChange(measureValue);
          }
        } else { // 上层指标且不是同类型的
          MeasureHost parent = affectedHost;
          while ((parent = parent.parent()) != null) {
            if (parent.getHostType() == currentMHT) {

              measureValue = doCalculate(parent, value.getIsFloat(), key.getOperatorSequence());
              this.measureValueRepository.save(measureValue);
              measureValue.setReady(true);
              break;
            }
          }
        }
      }

      // 目前只有非浮动指标才给界面发送通知
      if (measureValue.isReady() && !measureValue.isFloat()) {
        log.info(key.toString());
        log.info(value.toString());
        value.setValue(measureValue.getValue());
        kafkaTemplate.send(MEASURE_TOPIC, key, value);
      }
      return measureValue;
    } else { // 清算指标
    }
    return null;
  }

  /**
   * 子类实现交易指标的具体计算
   * 
   * @param measureHostId
   * @param isFloat
   * @param version
   * @return
   */
  protected abstract TradingMeasureValue doCalculate(MeasureHost measureHost, boolean isFloat,
      String version);

  /**
   * 注册到指标库
   * <p>
   * PostConstruct表示在构造函数之后由spring自动执行
   */
  @PostConstruct
  private void register() {
    measureRepository.register(this);
  }
}

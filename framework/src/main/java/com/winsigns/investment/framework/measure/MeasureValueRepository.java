package com.winsigns.investment.framework.measure;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 指标值仓库
 * 
 * <p>
 * 用来存放具体的指标值
 * 
 * @author Created by colin on 2017/3/2.
 * @author yimingjin
 *
 */
@Component
public class MeasureValueRepository {

  public final static String NO_CHANGE = "no-change";

  protected final static String SEGMENTATION = ":";

  protected final static Long INTERVAL = 100L;

  @Autowired
  StringRedisTemplate measureRepository;

  /**
   * 保存交易指标值
   * 
   * @param value
   */
  public void save(TradingMeasureValue value) {

    measureRepository.opsForValue().set(key(value), String.valueOf(value.getValue()));

    updateIndex(value);
  }

  /**
   * 保存没有变动的交易值
   * 
   * @param value
   */
  public void saveNoChange(TradingMeasureValue value) {
    measureRepository.opsForValue().set(key(value), NO_CHANGE);

    updateIndex(value);
  }

  /**
   * 更新交易指标值的索引
   * 
   * @param value
   */
  protected void updateIndex(TradingMeasureValue value) {
    String version = measureRepository.opsForValue().get(latestIndexKey(value));
    if (version == null || (version.compareTo(value.getVersion()) < 0)) {
      measureRepository.opsForValue().set(latestIndexKey(value),
          String.valueOf(value.getVersion()));
    }
  }

  /**
   * 保存清算指标值
   * 
   * @param value
   */
  public void save(ClearanceMeasureValue value) {
    measureRepository.opsForValue().set(key(value), String.valueOf(value.getValue()));

    updateIndex(value);
  }

  /**
   * 更新清算指标值的索引
   * 
   * @param value
   */
  protected void updateIndex(ClearanceMeasureValue value) {
    String clearDate = measureRepository.opsForValue().get(indexKey(value));
    if (clearDate == null || clearDate.compareTo(value.getClearDate()) < 0) {
      measureRepository.opsForValue().set(indexKey(value), value.getClearDate());
    }
  }

  /**
   * 获取特定的交易指标
   * 
   * @param measureHost 宿主
   * @param measure 指标
   * @param isFloat 是否浮动
   * @param version 版本
   * @return 返回交易指标值的一个实例
   */
  public TradingMeasureValue getMeasureValue(MeasureHost measureHost, Measure measure,
      boolean isFloat, String version) {
    TradingMeasureValue newValue = new TradingMeasureValue(measureHost, measure, isFloat, version);

    String result = measureRepository.opsForValue().get(key(newValue));
    if (result != null) {
      if (!result.equals(NO_CHANGE)) {
        newValue.setValue(Double.valueOf(result));
        newValue.setReady(true);
      } else {
        updateValue(newValue);
      }
    }
    return newValue;
  }

  /**
   * 获取最新的特定交易指标
   * 
   * @param measureHost 宿主
   * @param measure 指标
   * @param isFloat 是否浮动
   * @return 返回交易指标值的一个实例，其版本是最新的
   */
  public TradingMeasureValue getMeasureValue(MeasureHost measureHost, Measure measure,
      boolean isFloat) {

    TradingMeasureValue newValue = new TradingMeasureValue(measureHost, measure, isFloat);
    String latestVersion = measureRepository.opsForValue().get(latestIndexKey(newValue));
    if (latestVersion != null) {
      return getMeasureValue(measureHost, measure, isFloat, latestVersion);
    }
    return newValue;
  }

  /**
   * 更新NO_CHANGE的值
   * 
   * @param newValue
   * @return
   */
  private TradingMeasureValue updateValue(TradingMeasureValue newValue) {
    List<String> keys = getKeys(newValue.getVersion(), indexKey(newValue));
    List<String> values = measureRepository.opsForValue().multiGet(keys);
    for (int index = 1; index < values.size(); ++index) {
      if (values.get(index) == null) { // TODO 检查超时
        break;
      }
      if (!values.get(index).equals(NO_CHANGE)) { // 查找第一个不为NO_CHANGE的序号
        String tmp = values.get(index);
        measureRepository.opsForValue().set(keys.get(0), tmp);
        newValue.setValue(Double.valueOf(tmp));
        newValue.setReady(true);
        break;
      }
    }
    return newValue;
  }

  /**
   * 根据版本号获取批量的历史版本
   * <p>
   * TODO 暂时考虑获取100个版本
   * 
   * @param version 当前版本
   * @param indexKey 版本前缀
   * @return
   */
  private List<String> getKeys(String version, String indexKey) {

    ArrayList<String> keys = new ArrayList<String>();
    Long beginVersion = Long.valueOf(version);

    for (Long index = 0L; index < INTERVAL; ++index) { // 暂时认为100次一定能找到
      Long nowVersion = beginVersion - index;
      if (nowVersion <= 0) {
        break;
      }
      keys.add(indexKey + SEGMENTATION + nowVersion);
    }
    return keys;
  }

  /**
   * 获取上一交易日的清算指标值
   * 
   * @param measureHost 宿主
   * @param measure 指标
   * @return 返回上一日的清算指标值
   */
  public ClearanceMeasureValue getLatestClearanceValue(MeasureHost measureHost, Measure measure) {

    ClearanceMeasureValue newValue = new ClearanceMeasureValue(measureHost, measure);

    String lastClearDate = measureRepository.opsForValue().get(indexKey(newValue));

    if (lastClearDate != null) {
      newValue.setClearDate(lastClearDate);
      Double value = Double.valueOf(measureRepository.opsForValue().get(key(newValue)));
      if (value != null) {
        newValue.setValue(value);
        newValue.setReady(true);
      }
    }
    return newValue;
  }

  /**
   * 获取指定清算日期的清算指标值
   * 
   * @param measureHost 宿主
   * @param measure 指标
   * @param clearDate 清算日期
   * @return 指定日期的清算指标值
   */
  public ClearanceMeasureValue getClearanceValue(MeasureHost measureHost, Measure measure,
      String clearDate) {

    ClearanceMeasureValue newValue = new ClearanceMeasureValue(measureHost, measure, clearDate);
    Double value = Double.valueOf(measureRepository.opsForValue().get(key(newValue)));
    if (value != null) {
      newValue.setValue(value);
      newValue.setReady(true);
    }
    return newValue;
  }

  /**
   * 获取指定清算日期的清算指标值
   * 
   * @param measureHost 宿主
   * @param measure 指标
   * @param clearDate 清算日期
   * @return 指定日期的清算指标值
   */
  public ClearanceMeasureValue getClearanceValue(MeasureHost measureHost, Measure measure,
      Date clearDate) {

    return getClearanceValue(measureHost, measure,
        ClearanceMeasureValue.dateFormat.format(clearDate));
  }

  // TODO 需要交易日
  public ClearanceMeasureValue getClearanceValue(MeasureHost measureHost, Measure measure,
      int offsetDays) {
    String clearDate = "";
    return getClearanceValue(measureHost, measure, clearDate);
  }

  /**
   * 获取指标值的关键字
   * 
   * @param value
   * @return 关键字
   */
  protected String baseKey(MeasureValue value) {
    return value.getMeasureHost().getHostType().getName() + SEGMENTATION
        + value.getMeasureHost().getId() + SEGMENTATION + value.getMeasure().getName();
  }

  /**
   * 获取交易指标值的关键字
   * 
   * @param value
   * @return
   */
  protected String key(TradingMeasureValue value) {
    return baseKey(value) + SEGMENTATION + value.isFloat() + SEGMENTATION + value.getVersion();
  }

  /**
   * 获取交易指标值的索引
   * 
   * @param value
   * @return
   */
  protected String latestIndexKey(TradingMeasureValue value) {
    return indexKey(value) + SEGMENTATION + "latest";
  }

  protected String indexKey(TradingMeasureValue value) {
    return baseKey(value) + SEGMENTATION + value.isFloat();
  }

  /**
   * 获取清算指标值的关键字
   * 
   * @param value
   * @return
   */
  protected String key(ClearanceMeasureValue value) {
    return baseKey(value) + SEGMENTATION + value.getClearDate();
  }

  /**
   * 获取清算指标值的索引
   * 
   * @param value
   * @return
   */
  protected String indexKey(ClearanceMeasureValue value) {
    return baseKey(value) + SEGMENTATION + "latest";
  }
}

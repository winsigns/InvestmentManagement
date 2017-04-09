package com.winsigns.investment.framework.measure;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 交易指标值
 * 
 * <p>
 * 清算指标值带有日期
 * 
 * @author Created by colin on 2017/3/3.
 * @since 0.0.2
 */
public class ClearanceMeasureValue extends MeasureValue {

  public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

  @Setter
  @Getter
  private String clearDate; // 日期暂时考虑直接为字符串

  /**
   * 构造一个清算指标值
   * <p>
   * 该值是完整的
   * 
   * @param measureHost 指标宿主
   * @param measure 指标
   * @param value 值
   * @param clearDate 清算日期
   */
  public ClearanceMeasureValue(MeasureHost measureHost, Measure measure, String clearDate,
      double value) {
    super(measureHost, measure, value, false);
    this.clearDate = clearDate;
  }

  /**
   * 构造一个清算指标值
   * <p>
   * 该值是完整的
   * 
   * @param measureHost 指标宿主
   * @param measure 指标
   * @param value 值
   * @param clearDate 清算日期
   */
  public ClearanceMeasureValue(MeasureHost measureHost, Measure measure, Date clearDate,
      double value) {
    this(measureHost, measure, dateFormat.format(clearDate), value);
  }

  /**
   * 构造一个清算指标值
   * <p>
   * 具体值为初始值
   * 
   * @param measureHost 指标宿主
   * @param measure 指标
   * @param clearDate 清算日期
   */
  public ClearanceMeasureValue(MeasureHost measureHost, Measure measure, String clearDate) {
    this(measureHost, measure, clearDate, 0.0);
  }

  /**
   * 构造一个清算指标值
   * <p>
   * 具体值为初始值
   * 
   * @param measureHost 指标宿主
   * @param measure 指标
   * @param clearDate 清算日期
   */
  public ClearanceMeasureValue(MeasureHost measureHost, Measure measure, Date clearDate) {
    this(measureHost, measure, dateFormat.format(clearDate));
  }

  /**
   * 构造一个清算指标值
   * <p>
   * 清算日期和具体值为初始值
   * 
   * @param measureHost 指标宿主
   * @param measure 指标
   */
  public ClearanceMeasureValue(MeasureHost measureHost, Measure measure) {
    this(measureHost, measure, new Date(), 0.0);
  }

}

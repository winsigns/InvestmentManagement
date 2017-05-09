package com.winsigns.investment.framework.chrono;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.winsigns.investment.framework.integration.AbstractIntegration;

import lombok.extern.slf4j.Slf4j;

/**
 * 封装了向time-service访问的接口
 * <p>
 * 用来获取系统日期，时间
 * 
 * @author yimingjin
 * @since 0.0.4
 *
 */
@Component
@Slf4j
public class ChronoIntegration extends AbstractIntegration {

  private static final String SYSTEM_DATE_URL = "/system-date";

  private static final String SYSTEM_TIME_URL = "/system-time";

  private static final DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

  private static final DateFormat timestampFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss.SSS");

  @Override
  public String getIntegrationName() {
    // TODO 目前服务太多，小功能暂时合并为同一个服务
    // return "sequence-service";
    return "general-service";
  }

  /**
   * 获取系统日期
   * 
   * @return 获取的日期
   */
  @HystrixCommand(fallbackMethod = "getDefaultDate")
  public Date getDate() {
    return getSystemChrono(SYSTEM_DATE_URL, dateFormat);
  }

  /**
   * 获取系统时间戳
   * 
   * @return 返回时间戳
   */
  @HystrixCommand(fallbackMethod = "getDefaultDate")
  public Date getTimeStamp() {
    return getSystemChrono(SYSTEM_TIME_URL, timestampFormat);
  }

  public Date getDefaultDate() {
    return new Date();
  }

  private Date getSystemChrono(String url, DateFormat format) {
    String dateStr = this.getNode(getIntegrationURI() + url).get(url.substring(8)).asText();
    Date date = null;
    try {
      date = format.parse(dateStr);
    } catch (ParseException e) {
      log.error(e.getLocalizedMessage());
      throw new RuntimeException(e);
    }
    return date;
  }
}

package com.winsigns.investment.framework.hal;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.winsigns.investment.framework.spring.SpringManager;

/**
 * 增加Link的替换功能
 * 
 * @author yimingjin
 * @since 0.0.4
 */
public class HalHelper {

  private static final String URI_PATTERN = "(https?|ftp|file)(://)([^/]*)(/.*)";
  private static final String SERVICE_PATTERN = "[-a-zA-Z]*-service";
  private static final String applicationName =
      SpringManager.getApplicationContext().getEnvironment().getProperty("spring.application.name");

  /**
   * 将IP替换成服务名
   * 
   * @param link
   * @return
   */
  protected String replaceServiceName(String link) {
    return checkReplace(link) ? link.replaceFirst("://[^/]*", "://" + applicationName) : link;
  }

  /**
   * 检查是否需要替换
   * 
   * @param link
   * @return
   */
  protected boolean checkReplace(String link) {

    Pattern p = Pattern.compile(URI_PATTERN);
    Matcher matcher = p.matcher(link);
    if (matcher.matches()) {
      String checkStr = matcher.group(3);
      // 如果此字符串为xxxxx-service 则不需要替换，否则替换
      if (checkStr.matches(SERVICE_PATTERN)) {
        return false;
      }
      return true;
    }
    return false;
  }
}

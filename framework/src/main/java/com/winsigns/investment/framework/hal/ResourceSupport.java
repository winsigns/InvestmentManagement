package com.winsigns.investment.framework.hal;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.hateoas.Link;
import org.springframework.util.Assert;

import com.winsigns.investment.framework.spring.SpringManager;

public class ResourceSupport extends org.springframework.hateoas.ResourceSupport {

  private static final String URI_PATTERN = "(https?|ftp|file)(://)([^/]*)(/.*)";
  private static final String SERVICE_PATTERN = "[-a-zA-Z]*-service";
  private static final String applicationName =
      SpringManager.getApplicationContext().getEnvironment().getProperty("spring.application.name");

  /**
   * Adds the given link to the resource.
   * 
   * @param link
   */
  @Override
  public void add(Link link) {
    Assert.notNull(link, "Link must not be null!");
    super.add(new Link(replaceServiceName(link.getHref()), link.getRel()));
  }

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

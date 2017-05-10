package com.winsigns.investment.framework.hal;

import org.springframework.hateoas.Link;
import org.springframework.util.Assert;

/**
 * 支持link的替换功能
 * 
 * @author yimingjin
 * @since 0.0.4
 */
public class ResourceSupport extends org.springframework.hateoas.ResourceSupport {

  static protected HalHelper halHelper = new HalHelper();

  @Override
  public void add(Link link) {
    Assert.notNull(link, "Link must not be null!");
    super.add(new Link(halHelper.replaceServiceName(link.getHref()), link.getRel()));
  }


}

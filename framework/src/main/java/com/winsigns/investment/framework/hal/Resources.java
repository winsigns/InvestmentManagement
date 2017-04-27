package com.winsigns.investment.framework.hal;

import java.util.Arrays;

import org.springframework.hateoas.Link;

/**
 * 支持link的替换功能
 * 
 * @author yimingjin
 * @since 0.0.4
 */
public class Resources<T> extends org.springframework.hateoas.Resources<T> {

  static protected HalHelper halHelper = new HalHelper();

  public Resources(Iterable<T> content, Iterable<Link> links) {

    super(content);

    for (Link link : links) {
      add(new Link(halHelper.replaceServiceName(link.getHref()), link.getRel()));
    }

  }

  public Resources(Iterable<T> content, Link... links) {
    this(content, Arrays.asList(links));
  }

}


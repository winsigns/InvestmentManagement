package com.winsigns.investment.framework.integration;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;

/**
 * 通过服务发现与服务间进行交互
 * 
 * @author yimingjin
 * @since 0.0.1
 */
public abstract class AbstractIntegration extends BaseIntegration {

  @Autowired
  LoadBalancerClient loadBalancer;

  protected URI getIntegrationURI() {
    return loadBalancer.choose(getIntegrationName()).getUri();
  }

  /**
   * 子类定义该交互的名字
   * 
   * @return 交互的服务名
   */
  public abstract String getIntegrationName();

}

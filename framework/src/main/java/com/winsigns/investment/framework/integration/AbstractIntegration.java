package com.winsigns.investment.framework.integration;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.client.RestOperations;

/**
 * 服务间调用的基类
 * 
 * <p>
 * 注入了LoadBalancerClient和RestOperations
 * 
 * @author yimingjin
 * @since 0.0.1
 */
public abstract class AbstractIntegration {

  @Autowired
  LoadBalancerClient loadBalancer;

  @Autowired
  protected RestOperations restTemplate;

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

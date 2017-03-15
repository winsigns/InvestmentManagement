package com.winsigns.investment.framework.integration;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.client.RestOperations;

public abstract class IntegrationBase implements IIntegration {

  @Autowired
  LoadBalancerClient loadBalancer;

  @Autowired
  protected RestOperations restTemplate;

  protected URI getIntegrationURI() {
    return loadBalancer.choose(getIntegrationName()).getUri();
  }

}

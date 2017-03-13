package com.winsigns.investment.webGateway;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
public class WebGatewayApplication {

  public static void main(String[] args) {
    new SpringApplicationBuilder(WebGatewayApplication.class).web(true).run(args);
  }
}

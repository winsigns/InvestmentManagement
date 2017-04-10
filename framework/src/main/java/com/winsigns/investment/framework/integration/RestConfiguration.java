package com.winsigns.investment.framework.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 注入rest的调用
 * 
 * @author yimingjin
 * @since 0.0.2
 */
@Configuration
public class RestConfiguration {

  @Bean
  RestTemplate restTemplate() {
    return new RestTemplate();
  }

}

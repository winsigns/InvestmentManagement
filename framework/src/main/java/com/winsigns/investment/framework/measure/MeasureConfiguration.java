package com.winsigns.investment.framework.measure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * Created by colin on 2017/3/3.
 */

@Configuration
@ComponentScan
public class MeasureConfiguration {

  @Autowired
  RedisConnectionFactory connectionFactory;

  @Bean
  public MeasureValueRepository measureValueRepository() {
    return new MeasureValueRepository();
  }

  @Bean
  public MeasureVersionIntegration measureVersionIntegration() {
    return new MeasureVersionIntegration();
  }
}

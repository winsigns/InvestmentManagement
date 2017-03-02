package com.winsigns.investment.framework.kafka;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaBean {

  @Bean
  public KafkaConfiguration kafkaConfiguration() {
    return new KafkaConfiguration();
  }

}

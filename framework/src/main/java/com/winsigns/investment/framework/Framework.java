package com.winsigns.investment.framework;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.winsigns.investment.framework.hal.HalBrowser;
import com.winsigns.investment.framework.kafka.KafkaConfiguration;

@Configuration
public class Framework {

  @Bean
  public KafkaConfiguration KafkaConfiguration() {
    return new KafkaConfiguration();
  }

  @Bean
  public HalBrowser halBrowser() {
    return new HalBrowser();
  }
}

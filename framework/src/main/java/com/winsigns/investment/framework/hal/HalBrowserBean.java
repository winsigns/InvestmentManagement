package com.winsigns.investment.framework.hal;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HalBrowserBean {

  @Bean
  public HalBrowser halBrowser() {
    return new HalBrowser();
  }

}

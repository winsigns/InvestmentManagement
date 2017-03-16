package com.winsigns.investment.framework.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

@Service // 注入spring容器
@Configuration
public class SpringManager implements ApplicationListener<ContextRefreshedEvent> {
  private static ApplicationContext applicationContext = null;

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    if (applicationContext == null) {
      applicationContext = event.getApplicationContext();
    }
  }

  public static ApplicationContext getApplicationContext() {
    return applicationContext;
  }

  @Configuration
  protected static class SpringConfiguration {
    @Bean
    SpringManager springManager() {
      return new SpringManager();
    }
  }
}

package com.winsigns.investment.framework.i18n;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Configuration
public class i18nConfiguration {

  @Bean
  public MessageSource messageSource() {
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.setBasename("i18n/message");
    messageSource.setDefaultEncoding("UTF-8");
    return messageSource;
  }

  @Component
  protected static class MessageSourceInjector {

    @Autowired
    MessageSource messageSource;

    @PostConstruct
    public void postConstruct() {
      i18nHelper.messageSource = messageSource;
    }

    @Bean
    public MessageSourceInjector messageSourceInjector() {
      return new MessageSourceInjector();
    }
  }
}

package com.winsigns.investment.fundService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;

@SpringBootApplication
public class FundServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(FundServiceApplication.class, args);
  }

  @Bean
  public MessageSource messageSource() {
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.setBasename("i18n/message");
    messageSource.setDefaultEncoding("UTF-8");
    return messageSource;
  }
}

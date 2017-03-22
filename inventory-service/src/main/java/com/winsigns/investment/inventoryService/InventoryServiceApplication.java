package com.winsigns.investment.inventoryService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;

@SpringBootApplication
@EnableDiscoveryClient
@EnableHypermediaSupport(type = {HypermediaType.HAL})
public class InventoryServiceApplication {

  public static void main(String[] args) {

    SpringApplication.run(InventoryServiceApplication.class, args);

  }

  @Bean
  public MessageSource messageSource() {
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.setBasename("i18n/message");
    messageSource.setDefaultEncoding("UTF-8");
    return messageSource;
  }

}

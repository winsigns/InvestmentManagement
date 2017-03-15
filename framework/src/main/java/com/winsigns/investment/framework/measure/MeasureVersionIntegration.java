package com.winsigns.investment.framework.measure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.winsigns.investment.framework.integration.IntegrationBase;

@Component
@Configuration
public class MeasureVersionIntegration extends IntegrationBase {

  private static final String MEASURE_VERSION_URL = "/measure-versions";

  @Override
  public String getIntegrationName() {
    return "sequence-service";
  }

  public String getMeasureVersion() {
    return restTemplate.postForObject(getIntegrationURI() + MEASURE_VERSION_URL, null,
        String.class);
  }

  @Bean
  public MeasureVersionIntegration measureVersionIntegration() {
    return new MeasureVersionIntegration();
  }

}

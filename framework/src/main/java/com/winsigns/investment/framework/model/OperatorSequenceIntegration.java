package com.winsigns.investment.framework.model;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.winsigns.investment.framework.integration.IntegrationBase;

@Component
@Configuration
public class OperatorSequenceIntegration extends IntegrationBase {

  private static final String MEASURE_VERSION_URL = "/operator-sequences";

  @Override
  public String getIntegrationName() {
    return "sequence-service";
  }

  public String getSequence() {
    return restTemplate.getForObject(getIntegrationURI() + MEASURE_VERSION_URL, String.class);
  }

}

package com.winsigns.investment.tradeService.integration;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.winsigns.investment.framework.integration.AbstractIntegration;
import com.winsigns.investment.framework.spring.SpringManager;

/**
 * 与invest-service的交互
 * 
 * @author yimingjin
 *
 */
@Component
public class InvestServiceIntegration extends AbstractIntegration {

  static final String INVEST_SERVICE = "invest-service";

  private String instructionURL = "/instructions/%d";

  static public InvestServiceIntegration getInvestServiceIntegration() {
    return SpringManager.getApplicationContext().getBean(InvestServiceIntegration.class);
  }

  @Override
  public String getIntegrationName() {
    return INVEST_SERVICE;
  }

  public Long getInstructionSecurityId(Long instructionId) {

    ObjectNode node =
        getNode(this.getIntegrationURI() + String.format(instructionURL, instructionId));

    return node.get("securityId").asLong();

  }

}

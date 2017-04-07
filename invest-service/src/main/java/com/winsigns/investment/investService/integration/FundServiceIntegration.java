package com.winsigns.investment.investService.integration;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import com.jayway.jsonpath.JsonPath;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.winsigns.investment.framework.integration.AbstractIntegration;

/**
 * 与fund-service的交互
 * 
 * @author yimingjin
 *
 */
@Component
public class FundServiceIntegration extends AbstractIntegration {

  private String portfolioURL = "/portfolios/%d";

  @Override
  public String getIntegrationName() {
    return "fund-service";
  }

  @HystrixCommand(fallbackMethod = "defaultPortfolioInvestManager")
  public Long getPortfolioInvestManager(Long portfolioId) {
    System.out.println(this.getIntegrationURI() + String.format(portfolioURL, portfolioId));
    try {
      ResponseEntity<String> resultStr = restTemplate.getForEntity(
          this.getIntegrationURI() + String.format(portfolioURL, portfolioId), String.class);
      return JsonPath.read(resultStr.getBody(), "$.investManagerId");
    } catch (RestClientException e) {
      return null;
    }
  }

  public Long defaultPortfolioInvestManager(Long portfolioId) {
    return null;
  }
}

package com.winsigns.investment.investService.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
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

  private Logger log = LoggerFactory.getLogger(FundServiceIntegration.class);

  private String portfolioURL = "/portfolios/%d";

  @Override
  public String getIntegrationName() {
    return "fund-service";
  }

  @HystrixCommand(fallbackMethod = "defaultPortfolioInvestManager")
  public Long getPortfolioInvestManager(Long portfolioId) {
    try {
      ResponseEntity<String> resultStr = restTemplate.getForEntity(
          this.getIntegrationURI() + String.format(portfolioURL, portfolioId), String.class);
      // 从jsonpath解析出来的数字，会自动转换成Integer或者Long，因此统一转成String先
      return Long.valueOf(JsonPath.read(resultStr.getBody(), "$.investManagerId").toString());
    } catch (RestClientException e) {
      log.warn(e.getMessage());
      return null;
    } catch (PathNotFoundException e) {
      log.warn(e.getMessage());
      return null;
    }
  }

  public Long defaultPortfolioInvestManager(Long portfolioId) {
    return null;
  }
}

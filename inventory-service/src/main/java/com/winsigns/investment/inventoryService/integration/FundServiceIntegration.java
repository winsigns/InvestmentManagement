package com.winsigns.investment.inventoryService.integration;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.winsigns.investment.framework.integration.AbstractIntegration;

import lombok.extern.slf4j.Slf4j;

/**
 * 与fund-service的交互
 * 
 * @author yimingjin
 *
 */
@Component
@Slf4j
public class FundServiceIntegration extends AbstractIntegration {

  private String portfolioURL = "/portfolios/%d";

  @Override
  public String getIntegrationName() {
    return "fund-service";
  }

  public Long getFundAccountId(Long portfolioId) {
    try {
      ResponseEntity<String> resultStr = restTemplate.getForEntity(
          this.getIntegrationURI() + String.format(portfolioURL, portfolioId), String.class);
      String link = JsonPath.read(resultStr.getBody(), "$._links.fund-account.href");
      return Long.valueOf(link.substring(link.lastIndexOf('/') + 1));
    } catch (RestClientException e) {
      log.warn(e.getMessage());
      return null;
    } catch (PathNotFoundException e) {
      log.warn(e.getMessage());
      return null;
    }
  }
}

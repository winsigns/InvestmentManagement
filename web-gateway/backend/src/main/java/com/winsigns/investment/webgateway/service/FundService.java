package com.winsigns.investment.webgateway.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class FundService {

  private static String FUND_SERVICE = "fund-service";
  @Autowired

  private LoadBalancerClient loadBalancer;
  @Autowired

  private RestOperations restTemplate;

  private String getECACashPoolPath = "/funds";

  @HystrixCommand(fallbackMethod = "defaultECACashPools")
  public JsonNode findAllFunds() {
    ResponseEntity<String> resultStr = restTemplate.getForEntity(
        loadBalancer.choose(FUND_SERVICE).getUri() + getECACashPoolPath, String.class);
    return parseECACashPools(resultStr);
  }

  private JsonNode parseECACashPools(ResponseEntity<String> resp) {
    ObjectMapper objectMapper =
        new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    JsonNode node = null;
    try {
      node = objectMapper.readerFor(JsonNode.class).readValue(resp.getBody());
      JsonNode jsonNode = node.get("_embedded").get("funds");
      return jsonNode;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}


// public List<Fund> findAllFunds() {
// Fund fund = new Fund();
// fund.setFundCode("test");
//
// List<Fund> funds = new ArrayList<Fund>();
// funds.add(fund);
// System.out.println("rrrrrrr");
// System.out.println(fund);;
// return funds;
//
// }


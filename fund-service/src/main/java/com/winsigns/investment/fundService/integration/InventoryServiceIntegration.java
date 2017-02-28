package com.winsigns.investment.fundService.integration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.winsigns.investment.fundService.command.CreateCashPoolCommand;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Currency;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

/**
 * Created by colin on 2017/2/28.
 */

@Component
public class InventoryServiceIntegration {

  private static String INVENTORY_SERVICE = "inventory-service";

  @Autowired

  private LoadBalancerClient loadBalancer;
  @Autowired

  private RestOperations restTemplate;

  private String getECACashPoolPath = "/eca-cash-pools?externalCapitalAccountId=";

  private String postECACashPoolPath = "/eca-cash-pools";

  @HystrixCommand(fallbackMethod = "defaultECACashPools")
  public List getECACashPools(Long externalCapitalAccountId) {
    ResponseEntity<String> resultStr = restTemplate
        .getForEntity(loadBalancer.choose(INVENTORY_SERVICE).getUri() + getECACashPoolPath
            + externalCapitalAccountId, String.class);
    return parseECACashPools(resultStr);
  }

  private List parseECACashPools(ResponseEntity<String> resp) {
    ObjectMapper objectMapper = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    JsonNode node = null;
    try {
      node = objectMapper.readerFor(JsonNode.class).readValue(resp.getBody());
      JsonNode jsonNode = node.get("_embedded").get("eca-cash-pools");
      return objectMapper.convertValue(jsonNode, List.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private List defaultECACashPools(Long externalCapitalAccountId) {
    return new ArrayList();
  }

  public boolean createECACashPools(Long externalCapitalAccountId,
      Collection<Currency> supportedCurrency) {

    CreateCashPoolCommand cashChangeCommand = new CreateCashPoolCommand();
    cashChangeCommand.setExternalCapitalAccountId(externalCapitalAccountId);

    for (Currency currency : supportedCurrency) {
      cashChangeCommand.setCurrency(currency);

      HttpEntity<CreateCashPoolCommand> requestEntity =
          new HttpEntity<CreateCashPoolCommand>(cashChangeCommand);

      restTemplate
          .postForEntity(loadBalancer.choose(INVENTORY_SERVICE).getUri() + postECACashPoolPath,
              requestEntity, String.class);
    }
    return true;
  }
}

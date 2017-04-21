package com.winsigns.investment.tradeService.integration;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.winsigns.investment.framework.integration.AbstractIntegration;
import com.winsigns.investment.tradeService.command.ApplyResourceCommand;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class InventoryServiceIntegration extends AbstractIntegration {

  private final static String INVENTORY_SERVICE = "inventory-service";

  private final static String applyResourceURL = "/inventories";

  @Override
  public String getIntegrationName() {
    return INVENTORY_SERVICE;
  }

  public boolean applyResource(ApplyResourceCommand command) {

    log.info(command.toString());

    HttpEntity<ApplyResourceCommand> requestEntity = new HttpEntity<ApplyResourceCommand>(command);

    ResponseEntity<String> result = restTemplate
        .postForEntity(this.getIntegrationURI() + applyResourceURL, requestEntity, String.class);

    if (result.getStatusCode() == HttpStatus.OK) {
      return true;
    } else {
      return false;
    }
  }

}

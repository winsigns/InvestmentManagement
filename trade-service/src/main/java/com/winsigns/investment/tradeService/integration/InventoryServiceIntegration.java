package com.winsigns.investment.tradeService.integration;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.winsigns.investment.framework.integration.AbstractIntegration;
import com.winsigns.investment.tradeService.command.ApplyResourceCommand;

/**
 * 与清单服务的交互
 * 
 * @author yimingjin
 *
 */
@Component
public class InventoryServiceIntegration extends AbstractIntegration {

  private final static String INVENTORY_SERVICE = "inventory-service";

  private final static String applyResourceURL = "/inventories";

  @Override
  public String getIntegrationName() {
    return INVENTORY_SERVICE;
  }

  /**
   * 向清单服务申请资源
   * 
   * @param command
   * @return
   */
  public boolean applyResource(ApplyResourceCommand command) {

    String language = this.getHeaderParam("accept-language");
    command.setLanguage(language);
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

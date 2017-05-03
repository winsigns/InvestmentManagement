package com.winsigns.investment.tradeService.integration;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

import com.winsigns.investment.framework.integration.AbstractIntegration;
import com.winsigns.investment.framework.integration.ServiceNotFoundException;
import com.winsigns.investment.tradeService.command.ApplyResourceCommand;
import com.winsigns.investment.tradeService.exception.SendResourceApplicationFailed;

import lombok.extern.slf4j.Slf4j;

/**
 * 与清单服务的交互
 * 
 * @author yimingjin
 *
 */
@Component
@Slf4j
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
  public void applyResource(ApplyResourceCommand command) throws SendResourceApplicationFailed {

    String language = this.getHeaderParam("accept-language");
    command.setLanguage(language);
    HttpEntity<ApplyResourceCommand> requestEntity = new HttpEntity<ApplyResourceCommand>(command);

    try {
      ResponseEntity<String> result = restTemplate
          .postForEntity(this.getIntegrationURI() + applyResourceURL, requestEntity, String.class);
      log.info(result.getBody());
    } catch (RestClientResponseException e) {
      throw new SendResourceApplicationFailed(e.getResponseBodyAsString());
    } catch (RestClientException e) {
      throw new SendResourceApplicationFailed();
    } catch (ServiceNotFoundException e) {
      throw new SendResourceApplicationFailed(e);
    }
  }

}

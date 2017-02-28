package com.winsigns.investment.fundService.integration;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Currency;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.hateoas.client.Traverson.TraversalBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.jayway.jsonpath.PathNotFoundException;
import com.winsigns.investment.fundService.command.CreateCashPoolCommand;

import net.minidev.json.JSONArray;

@Component
public class CallInventoryService {

  @Value("${inventory-service}")
  private String host;

  private String urlGetECACashPool = "/eca-cash-pools?externalCapitalAccountId=%d";

  private String urlPostECACashPool = "/eca-cash-pools";

  public JSONArray getECACashPools(Long externalCapitalAccountId) {

    try {
      Traverson traverson =
          new Traverson(new URI(host + String.format(urlGetECACashPool, externalCapitalAccountId)),
              MediaTypes.HAL_JSON);

      TraversalBuilder newBuilder = traverson.follow();

      return newBuilder.toObject("$._embedded.eca-cash-pools");

    } catch (URISyntaxException e) {
      e.printStackTrace();
      return null;
    } catch (PathNotFoundException e) {
      // 正常不会走到这里
      return null;
    }
  }

  public boolean createECACashPools(Long externalCapitalAccountId,
      Collection<Currency> supportedCurrency) {
    RestTemplate restTemplate = new RestTemplate();

    CreateCashPoolCommand cashChangeCommand = new CreateCashPoolCommand();
    cashChangeCommand.setExternalCapitalAccountId(externalCapitalAccountId);

    for (Currency currency : supportedCurrency) {
      cashChangeCommand.setCurrency(currency);
      HttpEntity<CreateCashPoolCommand> requestEntity =
          new HttpEntity<CreateCashPoolCommand>(cashChangeCommand);
      try {
        restTemplate.postForEntity(new URI(host + urlPostECACashPool), requestEntity, String.class);
      } catch (URISyntaxException e) {
        e.printStackTrace();
        return false;
      }
    }
    return true;
  }
}

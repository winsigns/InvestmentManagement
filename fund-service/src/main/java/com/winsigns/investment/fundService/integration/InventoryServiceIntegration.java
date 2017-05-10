package com.winsigns.investment.fundService.integration;

import java.util.Collection;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jayway.jsonpath.Criteria;
import com.jayway.jsonpath.Filter;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.winsigns.investment.framework.constant.CurrencyCode;
import com.winsigns.investment.framework.integration.AbstractIntegration;
import com.winsigns.investment.fundService.command.CreateCashPoolCommand;
import com.winsigns.investment.fundService.model.ExternalCapitalAccount;
import com.winsigns.investment.fundService.model.FundAccount;

/**
 * Created by colin on 2017/2/28.
 */

@Component
public class InventoryServiceIntegration extends AbstractIntegration {

  private static String INVENTORY_SERVICE = "inventory-service";

  private String getECACashPoolPath = "/eca-cash-pools?externalCapitalAccountId=";

  private String postECACashPoolPath = "/eca-cash-pools";

  @Override
  public String getIntegrationName() {
    return INVENTORY_SERVICE;
  }

  /**
   * 获取资金池及相关信息
   * 
   * @param account 外部资金账户
   * @return
   */
  @HystrixCommand(fallbackMethod = "defaultECACashPools")
  public JsonNode getECACashPools(ExternalCapitalAccount account) {

    ObjectNode result =
        this.getNode(this.getIntegrationURI() + getECACashPoolPath + account.getId());
    ArrayNode root = result.with("_embedded").withArray("eca-cash-pools");
    for (JsonNode item : root) {
      if (item.getNodeType() == JsonNodeType.OBJECT) {
        ObjectNode item_ = (ObjectNode) item;
        Double cash = item.get("unassignedCapital").asDouble();
        ArrayNode detailsNode = this.objectMapper.createArrayNode();
        for (FundAccount fundAccount : account.getFund().getFundAccounts()) {
          List<Object> details = null;
          try {
            details = JsonPath.read(item.toString(), "$._embedded.capital-details[?]",
                Filter.filter(Criteria.where("fundAccountId").is(fundAccount.getId())));
          } catch (PathNotFoundException e) {
            // log.info(e.getMessage());
          }
          ObjectNode detailNode = this.objectMapper.createObjectNode();
          detailNode.put("fundAccountId", fundAccount.getId());
          detailNode.put("name", fundAccount.getName());
          if (details != null && !details.isEmpty()) {
            detailNode.put("id", JsonPath.read(details.get(0), "$.faCapitalPoolId").toString());
            Double cash_ = JsonPath.read(details.get(0), "$.cash");
            detailNode.put("cash", cash_);
            cash += cash_;
          }
          detailsNode.add(detailNode);
        }
        item_.set("capital-details", detailsNode);
        item_.put("cash", cash);
      }
    }
    return root;
  }

  public JsonNode defaultECACashPools(ExternalCapitalAccount account) {
    return this.objectMapper.createArrayNode();
  }

  /**
   * 创建资金池
   * 
   * @param externalCapitalAccountId
   * @param supportedCurrency
   * @return
   */
  public boolean createECACashPools(Long externalCapitalAccountId,
      Collection<CurrencyCode> supportedCurrency) {

    CreateCashPoolCommand cashChangeCommand = new CreateCashPoolCommand();
    cashChangeCommand.setExternalCapitalAccountId(externalCapitalAccountId);

    for (CurrencyCode currency : supportedCurrency) {
      cashChangeCommand.setCurrency(currency);

      HttpEntity<CreateCashPoolCommand> requestEntity =
          new HttpEntity<CreateCashPoolCommand>(cashChangeCommand);

      restTemplate.postForEntity(this.getIntegrationURI() + postECACashPoolPath, requestEntity,
          String.class);
    }
    return true;
  }
}

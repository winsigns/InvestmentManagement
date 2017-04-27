package com.winsigns.investment.fundService.resource;

import org.springframework.hateoas.core.Relation;

import com.winsigns.investment.framework.hal.HALResponse;
import com.winsigns.investment.fundService.model.ExternalTradeAccount;

@Relation(value = "external-trade-account", collectionRelation = "external-trade-accounts")
public class ExternalTradeAccountResource extends HALResponse<ExternalTradeAccount> {

  public ExternalTradeAccountResource(ExternalTradeAccount externalTradeAccount) {
    super(externalTradeAccount);
  }
}

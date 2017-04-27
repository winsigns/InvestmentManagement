package com.winsigns.investment.fundService.resource;

import org.springframework.hateoas.core.Relation;

import com.winsigns.investment.framework.hal.HALResponse;
import com.winsigns.investment.fundService.model.FundAccount;

@Relation(value = "fund-account", collectionRelation = "fund-accounts")
public class FundAccountResource extends HALResponse<FundAccount> {

  public FundAccountResource(FundAccount fundAccount) {
    super(fundAccount);
  }
}

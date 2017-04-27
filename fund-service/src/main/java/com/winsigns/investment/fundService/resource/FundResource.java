package com.winsigns.investment.fundService.resource;

import org.springframework.hateoas.core.Relation;

import com.winsigns.investment.framework.hal.HALResponse;
import com.winsigns.investment.fundService.model.Fund;

@Relation(value = "fund", collectionRelation = "funds")
public class FundResource extends HALResponse<Fund> {

  public FundResource(Fund fund) {
    super(fund);
  }

}

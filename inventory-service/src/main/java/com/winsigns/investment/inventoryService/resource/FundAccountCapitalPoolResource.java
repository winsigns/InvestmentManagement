package com.winsigns.investment.inventoryService.resource;

import org.springframework.hateoas.core.Relation;

import com.winsigns.investment.framework.hal.HALResponse;
import com.winsigns.investment.inventoryService.model.FundAccountCapitalPool;

import lombok.Getter;

@Relation(value = "fa-capital-pool", collectionRelation = "fa-capital-pools")
public class FundAccountCapitalPoolResource extends HALResponse<FundAccountCapitalPool> {

  @Getter
  final String currencyLabel;

  @Getter
  final String accountTypeLabel;

  public FundAccountCapitalPoolResource(FundAccountCapitalPool capitalPool) {
    super(capitalPool);

    this.currencyLabel = capitalPool.getCurrency().i18n();
    this.accountTypeLabel = capitalPool.getAccountType().i18n();

  }

}

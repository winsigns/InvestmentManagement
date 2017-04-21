package com.winsigns.investment.inventoryService.resource;

import com.winsigns.investment.framework.hal.HALResponse;
import com.winsigns.investment.inventoryService.model.CapitalDetail;

import lombok.Getter;

public class CapitalDetailResource extends HALResponse<CapitalDetail> {

  @Getter
  final Long fundAccountId;

  @Getter
  final Long externalCapitalId;

  public CapitalDetailResource(CapitalDetail capitalDetail) {
    super(capitalDetail);
    this.fundAccountId = capitalDetail.getCapitalPool().getFundAccountId();
    this.externalCapitalId = capitalDetail.getCashPool().getExternalCapitalAccountId();
  }

}

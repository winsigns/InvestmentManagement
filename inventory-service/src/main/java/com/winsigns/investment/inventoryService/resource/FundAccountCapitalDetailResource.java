package com.winsigns.investment.inventoryService.resource;

import com.winsigns.investment.framework.hal.HALResponse;
import com.winsigns.investment.inventoryService.model.FundAccountCapitalDetail;

public class FundAccountCapitalDetailResource extends HALResponse<FundAccountCapitalDetail> {

  public FundAccountCapitalDetailResource(FundAccountCapitalDetail fundAccountCapitalDetail) {
    super(fundAccountCapitalDetail);
  }

}

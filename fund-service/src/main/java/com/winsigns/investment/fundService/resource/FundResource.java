package com.winsigns.investment.fundService.resource;

import com.winsigns.investment.fundService.hal.HALResponse;
import com.winsigns.investment.fundService.model.Fund;

public class FundResource extends HALResponse<Fund> {

  public FundResource(Fund fund) {
    super(fund);
  }

}

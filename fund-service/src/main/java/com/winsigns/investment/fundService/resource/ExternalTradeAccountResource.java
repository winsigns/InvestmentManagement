package com.winsigns.investment.fundService.resource;

import com.winsigns.investment.fundService.hal.HALResponse;
import com.winsigns.investment.fundService.model.ExternalTradeAccount;

public class ExternalTradeAccountResource extends HALResponse<ExternalTradeAccount> {

  public ExternalTradeAccountResource(ExternalTradeAccount externalTradeAccount) {
    super(externalTradeAccount);
  }
}

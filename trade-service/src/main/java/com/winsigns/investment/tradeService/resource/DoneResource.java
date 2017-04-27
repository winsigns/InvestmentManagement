package com.winsigns.investment.tradeService.resource;

import com.winsigns.investment.framework.hal.HALResponse;
import com.winsigns.investment.tradeService.model.Done;

public class DoneResource extends HALResponse<Done> {

  public DoneResource(Done done) {
    super(done);

  }

}

package com.winsigns.investment.tradeService.resource;

import com.winsigns.investment.tradeService.hal.HALResponse;
import com.winsigns.investment.tradeService.model.Entrust;

public class EntrustResource extends HALResponse<Entrust> {

    public EntrustResource(Entrust entrust) {
        super(entrust);

    }

}

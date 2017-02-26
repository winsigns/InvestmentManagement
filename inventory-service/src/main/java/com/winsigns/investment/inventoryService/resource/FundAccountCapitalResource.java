package com.winsigns.investment.inventoryService.resource;

import com.winsigns.investment.inventoryService.hal.HALResponse;
import com.winsigns.investment.inventoryService.model.FundAccountCapital;

public class FundAccountCapitalResource extends HALResponse<FundAccountCapital> {

    public FundAccountCapitalResource(FundAccountCapital fundAccountCapital) {
        super(fundAccountCapital);
    }

}

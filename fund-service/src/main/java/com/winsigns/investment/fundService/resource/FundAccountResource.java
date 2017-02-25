package com.winsigns.investment.fundService.resource;

import com.winsigns.investment.fundService.hal.HALResponse;
import com.winsigns.investment.fundService.model.FundAccount;

public class FundAccountResource extends HALResponse<FundAccount> {

    public FundAccountResource(FundAccount fundAccount) {
        super(fundAccount);
    }
}

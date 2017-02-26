package com.winsigns.investment.fundService.resource;

import com.winsigns.investment.fundService.hal.HALResponse;
import com.winsigns.investment.fundService.model.ExternalCapitalAccount;

public class ExternalCapitalAccountResource extends HALResponse<ExternalCapitalAccount> {

    public ExternalCapitalAccountResource(ExternalCapitalAccount externalCapitalAccount) {

        super(externalCapitalAccount);
    }

}

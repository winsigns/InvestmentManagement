package com.winsigns.investment.fundService.resource;

import com.winsigns.investment.fundService.hal.HALResponse;
import com.winsigns.investment.fundService.model.Portfolio;

public class PortfolioResource extends HALResponse<Portfolio> {

    public PortfolioResource(Portfolio portfolio) {
        super(portfolio);
    }

}

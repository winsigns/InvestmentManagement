package com.winsigns.investment.fundService.resource;

import com.winsigns.investment.framework.hal.HALResponse;
import com.winsigns.investment.fundService.model.Portfolio;

public class PortfolioResource extends HALResponse<Portfolio> {

  final Long investManagerId;

  public PortfolioResource(Portfolio portfolio) {
    super(portfolio);
    this.investManagerId = portfolio.getFundAccount().getInvestManager().getId();
  }

}

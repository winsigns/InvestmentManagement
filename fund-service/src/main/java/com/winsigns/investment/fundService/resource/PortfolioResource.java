package com.winsigns.investment.fundService.resource;

import org.springframework.hateoas.core.Relation;

import com.winsigns.investment.framework.hal.HALResponse;
import com.winsigns.investment.fundService.model.Portfolio;

import lombok.Getter;

@Relation(value = "portfolio", collectionRelation = "portfolios")
public class PortfolioResource extends HALResponse<Portfolio> {

  @Getter
  final Long investManagerId;

  public PortfolioResource(Portfolio portfolio) {
    super(portfolio);
    if (portfolio.getFundAccount().getInvestManager() != null) {
      this.investManagerId = portfolio.getFundAccount().getInvestManager().getId();
    } else {
      this.investManagerId = null;
    }
  }

}

package com.winsigns.investment.inventoryService.command;

import com.winsigns.investment.inventoryService.constant.PositionType;

public class CreatePositionCommand {

  private Long portfolioId;

  private Long externalTradeAccountId;

  private Long securityId;

  private PositionType positionType;

  public Long getPortfolioId() {
    return portfolioId;
  }

  public void setPortfolioId(Long portfolioId) {
    this.portfolioId = portfolioId;
  }

  public Long getExternalTradeAccountId() {
    return externalTradeAccountId;
  }

  public void setExternalTradeAccountId(Long externalTradeAccountId) {
    this.externalTradeAccountId = externalTradeAccountId;
  }

  public Long getSecurityId() {
    return securityId;
  }

  public void setSecurityId(Long securityId) {
    this.securityId = securityId;
  }

  public PositionType getPositionType() {
    return positionType;
  }

  public void setPositionType(PositionType positionType) {
    this.positionType = positionType;
  }



}

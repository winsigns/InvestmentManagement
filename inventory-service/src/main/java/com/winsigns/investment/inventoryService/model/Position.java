package com.winsigns.investment.inventoryService.model;

import javax.persistence.Entity;

import org.springframework.hateoas.core.Relation;

import com.winsigns.investment.framework.model.AbstractEntity;
import com.winsigns.investment.inventoryService.constant.PositionType;

@Entity
@Relation(value = "position", collectionRelation = "positions")
public class Position extends AbstractEntity {

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

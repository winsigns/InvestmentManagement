package com.winsigns.investment.inventoryService.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.winsigns.investment.inventoryService.constant.PositionType;
import com.winsigns.investment.inventoryService.model.Position;

@Transactional
public interface PositionRepository extends JpaRepository<Position, Long> {
  public Position findByPortfolioIdAndExternalTradeAccountIdAndSecurityIdAndPositionType(
      Long portfolioId, Long externalTradeAccountId, Long securityId, PositionType positionType);
}

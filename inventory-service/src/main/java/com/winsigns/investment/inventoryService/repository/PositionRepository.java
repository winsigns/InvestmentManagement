package com.winsigns.investment.inventoryService.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.winsigns.investment.inventoryService.model.Position;

public interface PositionRepository extends JpaRepository<Position, Long> {
  public Position findByPortfolioIdAndExternalTradeAccountIdAndSecurityId(Long portfolioId,
      Long externalTradeAccountId, Long securityId);
}

package com.winsigns.investment.inventoryService.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.winsigns.investment.inventoryService.model.Position;

public interface PositionRepository extends JpaRepository<Position, Long> {
  public Position findByPortfolioIdAndExternalTradeAccountIdAndSecurityId(Long portfolioId,
      Long externalTradeAccountId, Long securityId);

  public List<Position> findByPortfolioIdAndSecurityId(Long portfolioId, Long securityId);
}

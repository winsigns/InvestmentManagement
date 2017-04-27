package com.winsigns.investment.inventoryService.position.stock;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StockPositionRepository extends JpaRepository<StockPosition, Long> {
  public StockPosition findByPortfolioIdAndSecurityIdAndExternalTradeAccountIdIsNull(
      Long portfolioId, Long securityId);

  public List<StockPosition> findByPortfolioIdAndSecurityIdOrderByCanSellPositionQuantityDesc(
      Long portfolioId, Long securityId);
}

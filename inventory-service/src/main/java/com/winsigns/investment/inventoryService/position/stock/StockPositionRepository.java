package com.winsigns.investment.inventoryService.position.stock;


import org.springframework.data.jpa.repository.JpaRepository;

public interface StockPositionRepository extends JpaRepository<StockPosition, Long> {
  public StockPosition findByPortfolioIdAndSecurityIdAndExternalTradeAccountIdIsNull(
      Long portfolioId, Long securityId);
}

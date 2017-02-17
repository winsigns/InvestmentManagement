package com.winsigns.investment.fundService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.winsigns.investment.fundService.model.Portfolio;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
}

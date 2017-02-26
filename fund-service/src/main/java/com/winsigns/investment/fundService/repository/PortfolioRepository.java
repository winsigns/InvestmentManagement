package com.winsigns.investment.fundService.repository;

import com.winsigns.investment.fundService.model.Portfolio;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

  public List<Portfolio> findByFundAccountId(Long fundAccountId);
}

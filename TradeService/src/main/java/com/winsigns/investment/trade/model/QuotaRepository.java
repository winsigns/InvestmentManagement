package com.winsigns.investment.trade.model;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

@Transactional 
public interface QuotaRepository extends JpaRepository<Quota, Long> {
	
	public Quota findByPortfolioIdAndSecurityIdAndExtCapitalAccountIdAndExtTradeAccountId(
			String instructId, String portfolioId, String extTradeAccountId, String extCapitalAccountId);
}

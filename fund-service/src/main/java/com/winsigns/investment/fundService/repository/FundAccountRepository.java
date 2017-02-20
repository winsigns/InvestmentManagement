package com.winsigns.investment.fundService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.winsigns.investment.fundService.model.FundAccount;

public interface FundAccountRepository extends JpaRepository<FundAccount, Long> {
	public List<FundAccount> findByFundId(Long fundId);
}

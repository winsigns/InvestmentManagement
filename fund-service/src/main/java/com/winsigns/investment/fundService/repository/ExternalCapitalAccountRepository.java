package com.winsigns.investment.fundService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.winsigns.investment.fundService.model.ExternalCapitalAccount;

@Transactional
public interface ExternalCapitalAccountRepository extends JpaRepository<ExternalCapitalAccount, Long> {
	public List<ExternalCapitalAccount> findByFundId(Long fundId);
}

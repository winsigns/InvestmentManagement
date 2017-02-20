package com.winsigns.investment.inventoryService.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.winsigns.investment.inventoryService.model.ExternalTradeAccount;

@Transactional
public interface ExternalTradeAccountRepository extends JpaRepository<ExternalTradeAccount, Long> {
	public List<ExternalTradeAccount> findByExternalCapitalAccountId(Long externalCapitalAccountId);
}

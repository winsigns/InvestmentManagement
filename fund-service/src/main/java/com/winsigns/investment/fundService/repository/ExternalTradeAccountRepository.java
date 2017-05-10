package com.winsigns.investment.fundService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.winsigns.investment.fundService.model.ExternalTradeAccount;

public interface ExternalTradeAccountRepository extends JpaRepository<ExternalTradeAccount, Long> {

  public List<ExternalTradeAccount> findByExternalCapitalAccountId(Long externalCapitalAccountId);
}

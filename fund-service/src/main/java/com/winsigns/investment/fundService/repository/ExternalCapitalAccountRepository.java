package com.winsigns.investment.fundService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.winsigns.investment.fundService.model.ExternalCapitalAccount;

public interface ExternalCapitalAccountRepository
    extends JpaRepository<ExternalCapitalAccount, Long> {

  public List<ExternalCapitalAccount> findByFundId(Long fundId);
}

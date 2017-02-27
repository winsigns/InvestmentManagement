package com.winsigns.investment.inventoryService.repository;

import java.util.Currency;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.winsigns.investment.inventoryService.model.FundAccountCapital;

@Transactional
public interface FundAccountCapitalRepository extends JpaRepository<FundAccountCapital, Long> {
  public FundAccountCapital findByFundAccountIdAndExternalCapitalAccountTypeAndCurrency(
      Long fundAccountId, String externalCapitalAccountType, Currency currency);
}

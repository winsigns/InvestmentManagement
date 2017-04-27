package com.winsigns.investment.inventoryService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.winsigns.investment.inventoryService.constant.CurrencyCode;
import com.winsigns.investment.inventoryService.model.ECACashPool;

public interface ECACashPoolRepository extends JpaRepository<ECACashPool, Long> {
  public ECACashPool findByExternalCapitalAccountIdAndCurrency(Long externalCapitalAccountId,
      CurrencyCode currency);

  public List<ECACashPool> findByExternalCapitalAccountId(Long externalCapitalAccountCapital);
}

package com.winsigns.investment.inventoryService.repository;

import java.util.Currency;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.winsigns.investment.inventoryService.model.ECACashPool;

@Transactional
public interface ECACashPoolRepository extends JpaRepository<ECACashPool, Long> {
    public ECACashPool findByFundIdAndExternalCapitalAccountIdAndCurrency(Long fundId, Long externalCapitalAccountId,
            Currency currency);

    public List<ECACashPool> findByFundIdAndExternalCapitalAccountId(Long fundId, Long externalCapitalAccountCapital);
}

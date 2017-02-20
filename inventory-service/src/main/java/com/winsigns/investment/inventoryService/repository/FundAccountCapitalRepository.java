package com.winsigns.investment.inventoryService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.winsigns.investment.inventoryService.model.FundAccountCapital;

@Transactional
public interface FundAccountCapitalRepository extends JpaRepository<FundAccountCapital, Long> {
	public FundAccountCapital findByFundAccountIdAndExternalCapitalAccountTypeIdAndCurrencyId(Long fundAccountId,
			Long externalCapitalAccountTypeId, Long currencyId);
}

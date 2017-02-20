package com.winsigns.investment.inventoryService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.winsigns.investment.inventoryService.model.ExternalCapitalAccountCapital;

@Transactional
public interface ExternalCapitalAccountCapitalRepository extends JpaRepository<ExternalCapitalAccountCapital, Long> {
	public ExternalCapitalAccountCapital findByExternalCapitalAccountIdAndCurrencyId(Long externalCapitalAccountId,
			Long currencyId);

	public List<ExternalCapitalAccountCapital> findByExternalCapitalAccountId(Long externalCapitalAccountCapital);
}

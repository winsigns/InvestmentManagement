package com.winsigns.investment.inventoryService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.winsigns.investment.inventoryService.model.FundAccountCapital;
import com.winsigns.investment.inventoryService.model.FundAccountCapitalDetail;

@Transactional
public interface FundAccountCapitalDetailRepository extends JpaRepository<FundAccountCapitalDetail, Long> {
	public FundAccountCapitalDetail findByFundAccountCapitalAndExternalCapitalAccountId(
			FundAccountCapital fundAccountCapital, Long externalCapitalAccountId);

}

package com.winsigns.investment.inventoryService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.winsigns.investment.inventoryService.model.FundAccountCapitalDetail;
import com.winsigns.investment.inventoryService.model.FundAccountCapitalDetailQuota;

@Transactional
public interface  FundAccountCapitalDetailQuotaRepository extends JpaRepository<FundAccountCapitalDetailQuota, String> {
	public List<FundAccountCapitalDetailQuota> findByFundAccountCapitalDetailAndNameIn(
			FundAccountCapitalDetail fundAccountCapitalDetail, List<String> names);
	
	public FundAccountCapitalDetailQuota findByFundAccountCapitalDetailAndName(
			FundAccountCapitalDetail fundAccountCapitalDetail, String names);
}

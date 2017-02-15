package com.winsigns.investment.inventory.capital.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface  FundAccountCapitalDetailQuotaRepository extends JpaRepository<FundAccountCapitalDetailQuota, String> {
	public List<FundAccountCapitalDetailQuota> findByFundAccountCapitalDetailAndNameIn(
			FundAccountCapitalDetail fundAccountCapitalDetail, List<String> names);
	
	public FundAccountCapitalDetailQuota findByFundAccountCapitalDetailAndName(
			FundAccountCapitalDetail fundAccountCapitalDetail, String names);
}

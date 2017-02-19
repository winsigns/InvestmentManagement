package com.winsigns.investment.inventoryService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.winsigns.investment.inventoryService.model.FundAccountCapitalDetail;
import com.winsigns.investment.inventoryService.model.FundAccountCapitalDetailId;

@Transactional
public interface FundAccountCapitalDetailRepository extends JpaRepository<FundAccountCapitalDetail, FundAccountCapitalDetailId> {

}

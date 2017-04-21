package com.winsigns.investment.inventoryService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.winsigns.investment.inventoryService.model.CapitalDetail;
import com.winsigns.investment.inventoryService.model.ECACashPool;
import com.winsigns.investment.inventoryService.model.FundAccountCapitalPool;

public interface CapitalDetailRepository extends JpaRepository<CapitalDetail, Long> {
  public CapitalDetail findByCapitalPoolAndCashPool(FundAccountCapitalPool capitalPool,
      ECACashPool ecaCashPool);

  public CapitalDetail findByCapitalPoolAndCashPoolIsNull(FundAccountCapitalPool capitalPool);

  public List<CapitalDetail> findByCapitalPoolOrderByCash(FundAccountCapitalPool capitalPool);

}



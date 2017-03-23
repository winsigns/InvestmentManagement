package com.winsigns.investment.inventoryService.repository;


import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.winsigns.investment.inventoryService.model.FundAccountCapitalDetail;
import com.winsigns.investment.inventoryService.model.FundAccountCapitalSerial;

public interface FundAccountCapitalSerialRepository
    extends JpaRepository<FundAccountCapitalSerial, Long> {

  @Query("select sum(assignedCash) from FundAccountCapitalSerial a where a.fundAccountCapitalDetail = :fundAccountCapitalDetail and a.assignedDate = :assignedDate")
  public Double findByFundAccountCapitalDetailAndAssignedDate(
      @Param("fundAccountCapitalDetail") FundAccountCapitalDetail fundAccountCapitalDetail,
      @Param("assignedDate") Date assignedDate);

}

package com.winsigns.investment.inventoryService.repository;


import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.winsigns.investment.inventoryService.model.ECACashPool;
import com.winsigns.investment.inventoryService.model.ECACashSerial;

public interface ECACashSerialRepository extends JpaRepository<ECACashSerial, Long> {

  @Query("select sum(assignedCash) from ECACashSerial a where a.ecaCashPool = :ecaCashPool and a.assignedDate = :assignedDate")
  public Double findByECACashPoolAndAssignedDate(@Param("ecaCashPool") ECACashPool ECACashPool,
      @Param("assignedDate") Date assignedDate);

}

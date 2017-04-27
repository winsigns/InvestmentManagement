package com.winsigns.investment.tradeService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.winsigns.investment.tradeService.constant.EntrustStatus;
import com.winsigns.investment.tradeService.model.Entrust;

public interface EntrustRepository extends JpaRepository<Entrust, Long> {


  List<Entrust> findByInstructionIdAndStatusNotOrderByEntrustTimeDesc(Long instructionId,
      EntrustStatus deleted);
}

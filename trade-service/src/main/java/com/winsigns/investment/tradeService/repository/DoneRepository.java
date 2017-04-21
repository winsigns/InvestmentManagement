package com.winsigns.investment.tradeService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.winsigns.investment.tradeService.model.Done;
import com.winsigns.investment.tradeService.model.Entrust;

public interface DoneRepository extends JpaRepository<Done, Long> {

  public List<Done> findByEntrustOrderByDoneTimeDesc(Entrust entrust);
}

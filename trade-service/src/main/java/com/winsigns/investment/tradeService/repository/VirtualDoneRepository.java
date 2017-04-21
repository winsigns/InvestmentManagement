package com.winsigns.investment.tradeService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.winsigns.investment.tradeService.model.VirtualDone;

public interface VirtualDoneRepository extends JpaRepository<VirtualDone, Long> {

}

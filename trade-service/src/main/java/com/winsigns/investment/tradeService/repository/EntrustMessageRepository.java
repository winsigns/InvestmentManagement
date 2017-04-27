package com.winsigns.investment.tradeService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.winsigns.investment.tradeService.model.EntrustMessage;

public interface EntrustMessageRepository extends JpaRepository<EntrustMessage, Long> {

}

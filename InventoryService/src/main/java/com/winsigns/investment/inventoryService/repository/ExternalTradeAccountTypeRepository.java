package com.winsigns.investment.inventoryService.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.winsigns.investment.inventoryService.model.ExternalTradeAccountType;

@Transactional
public interface ExternalTradeAccountTypeRepository extends JpaRepository<ExternalTradeAccountType, Long> {

}

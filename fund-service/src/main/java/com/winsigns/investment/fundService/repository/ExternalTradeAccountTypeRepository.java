package com.winsigns.investment.fundService.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.winsigns.investment.fundService.model.ExternalTradeAccountType;

@Transactional
public interface ExternalTradeAccountTypeRepository extends JpaRepository<ExternalTradeAccountType, Long> {

}

package com.winsigns.investment.fundService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.winsigns.investment.fundService.model.OpenedInvestmentScope;

@Transactional
public interface InvestmentScopeRepository extends JpaRepository<OpenedInvestmentScope, Long> {

}

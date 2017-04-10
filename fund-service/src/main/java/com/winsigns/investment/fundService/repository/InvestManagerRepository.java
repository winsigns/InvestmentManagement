package com.winsigns.investment.fundService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.winsigns.investment.fundService.model.InvestManager;

public interface InvestManagerRepository extends JpaRepository<InvestManager, Long> {

}

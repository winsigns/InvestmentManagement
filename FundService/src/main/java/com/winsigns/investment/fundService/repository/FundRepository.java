package com.winsigns.investment.fundService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.winsigns.investment.fundService.model.Fund;

public interface FundRepository extends JpaRepository<Fund, Long> {
}

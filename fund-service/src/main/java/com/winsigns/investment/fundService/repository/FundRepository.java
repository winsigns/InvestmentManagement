package com.winsigns.investment.fundService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.winsigns.investment.fundService.model.Fund;

public interface FundRepository extends JpaRepository<Fund, Long> {
	public List<Fund> findByCode(String code);
}

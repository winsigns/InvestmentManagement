package com.winsigns.investment.fundService.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FundRepository extends JpaRepository<Fund, Long> {
	public List<Fund> findByCode(String code);
}

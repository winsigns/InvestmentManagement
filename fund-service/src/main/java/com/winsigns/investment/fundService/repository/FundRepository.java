package com.winsigns.investment.fundService.repository;

import java.util.List;

import com.winsigns.investment.fundService.model.Fund;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FundRepository extends JpaRepository<Fund, Long> {
	public List<Fund> findByCode(String code);
}

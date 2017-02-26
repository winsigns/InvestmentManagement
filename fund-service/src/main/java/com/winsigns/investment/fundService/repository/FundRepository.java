package com.winsigns.investment.fundService.repository;

import com.winsigns.investment.fundService.model.Fund;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FundRepository extends JpaRepository<Fund, Long> {

}

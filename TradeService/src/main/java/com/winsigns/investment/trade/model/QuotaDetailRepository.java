package com.winsigns.investment.trade.model;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

@Transactional 
public interface QuotaDetailRepository extends JpaRepository<QuotaDetail, Long> {
}

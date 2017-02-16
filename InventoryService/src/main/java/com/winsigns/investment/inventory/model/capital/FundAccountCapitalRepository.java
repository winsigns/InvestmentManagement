package com.winsigns.investment.inventory.model.capital;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface FundAccountCapitalRepository extends JpaRepository<FundAccountCapital, FundAccountCapitalId> {

}

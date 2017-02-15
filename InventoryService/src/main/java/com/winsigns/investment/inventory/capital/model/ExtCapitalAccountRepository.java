package com.winsigns.investment.inventory.capital.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ExtCapitalAccountRepository extends JpaRepository<ExtCapitalAccountCapital, ExtCapitalAccountCapitalId> {

}

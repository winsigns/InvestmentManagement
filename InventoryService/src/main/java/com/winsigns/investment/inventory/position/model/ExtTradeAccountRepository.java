package com.winsigns.investment.inventory.position.model;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface ExtTradeAccountRepository extends JpaRepository<ExtTradeAccount, String> {

}

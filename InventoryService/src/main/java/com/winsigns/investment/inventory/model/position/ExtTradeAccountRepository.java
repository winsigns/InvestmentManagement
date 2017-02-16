package com.winsigns.investment.inventory.model.position;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface ExtTradeAccountRepository extends JpaRepository<ExtTradeAccount, String> {

}

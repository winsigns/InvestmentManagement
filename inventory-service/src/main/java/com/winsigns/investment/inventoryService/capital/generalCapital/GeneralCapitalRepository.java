package com.winsigns.investment.inventoryService.capital.generalCapital;

import org.springframework.data.jpa.repository.JpaRepository;

import com.winsigns.investment.framework.constant.CurrencyCode;

public interface GeneralCapitalRepository extends JpaRepository<GeneralCapitalPool, Long> {

  public GeneralCapitalPool findByFundAccountIdAndCurrency(Long fundAccountId,
      CurrencyCode currency);

}

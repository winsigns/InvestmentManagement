package com.winsigns.investment.inventoryService.command;

import com.winsigns.investment.framework.constant.CurrencyCode;

import lombok.Data;

@Data
public class CreateCashPoolCommand {

  private CurrencyCode currency;

  private Long externalCapitalAccountId;

}

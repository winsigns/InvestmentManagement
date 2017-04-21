package com.winsigns.investment.inventoryService.command;

import com.winsigns.investment.inventoryService.constant.CurrencyCode;

import lombok.Data;

@Data
public class CreateCashPoolCommand {

  private CurrencyCode currency;

  private Long externalCapitalAccountId;

}

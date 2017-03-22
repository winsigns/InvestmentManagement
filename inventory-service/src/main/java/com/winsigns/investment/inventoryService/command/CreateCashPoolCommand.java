package com.winsigns.investment.inventoryService.command;

import java.util.Currency;

import lombok.Data;

@Data
public class CreateCashPoolCommand {

  private Currency currency;

  private Long externalCapitalAccountId;

}

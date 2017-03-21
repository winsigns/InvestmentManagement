package com.winsigns.investment.inventoryService.command;

import java.util.Currency;

import lombok.Data;

@Data
public class CreateFundAccountCapitalCommand {

  private Long fundAccountId;

  private String externalCapitalAccountType;

  private Currency currency;

}

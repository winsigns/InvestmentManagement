package com.winsigns.investment.inventoryService.command;

import com.winsigns.investment.framework.constant.CurrencyCode;
import com.winsigns.investment.inventoryService.constant.ExternalCapitalAccountType;

import lombok.Data;

@Data
public class CreateFundAccountCapitalPoolCommand {

  private Long fundAccountId;

  private ExternalCapitalAccountType accountType;

  private CurrencyCode currency;

}

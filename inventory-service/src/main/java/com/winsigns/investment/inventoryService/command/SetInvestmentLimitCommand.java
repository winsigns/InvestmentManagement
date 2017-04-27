package com.winsigns.investment.inventoryService.command;

import lombok.Data;

@Data
public class SetInvestmentLimitCommand {

  private Long faCapitalPoolId;

  private Double investmentLimit;

}

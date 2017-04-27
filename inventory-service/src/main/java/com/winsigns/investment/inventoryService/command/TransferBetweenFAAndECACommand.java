package com.winsigns.investment.inventoryService.command;

import lombok.Data;

@Data
public class TransferBetweenFAAndECACommand {

  private Long faCapitalPoolId;

  private Long ecaCashPoolId;

  private Double occurAmount;

}

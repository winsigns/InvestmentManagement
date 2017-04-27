package com.winsigns.investment.inventoryService.command;

import lombok.Data;

@Data
public class TransferBetweenFAAndFACommand {

  private Long srcFACapitalPoolId;

  private Long dstFACapitalPoolId;

  private Double occurAmount;

}

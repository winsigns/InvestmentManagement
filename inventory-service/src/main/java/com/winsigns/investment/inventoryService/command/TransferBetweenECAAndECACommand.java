package com.winsigns.investment.inventoryService.command;

import lombok.Data;

@Data
public class TransferBetweenECAAndECACommand {

  private Long srcECACashPoolId;

  private Long dstECACashPoolId;

  private Double occurAmount;

}

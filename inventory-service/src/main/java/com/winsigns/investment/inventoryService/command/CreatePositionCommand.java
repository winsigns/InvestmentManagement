package com.winsigns.investment.inventoryService.command;

import lombok.Data;

@Data
public class CreatePositionCommand {

  private Long portfolioId;

  private Long externalTradeAccountId;

  private Long securityId;


}

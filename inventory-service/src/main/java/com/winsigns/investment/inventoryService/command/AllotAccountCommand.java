package com.winsigns.investment.inventoryService.command;

import lombok.Data;

@Data
public class AllotAccountCommand {

  private Double changedCapital;

  private Long matchECACashPoolId;

}

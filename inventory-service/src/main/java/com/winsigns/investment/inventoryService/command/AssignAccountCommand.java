package com.winsigns.investment.inventoryService.command;

import lombok.Data;

@Data
public class AssignAccountCommand {

  private Long ecaCashPoolId;

  private Double assignedCash;

}

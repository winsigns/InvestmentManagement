package com.winsigns.investment.inventoryService.command;

import lombok.Data;

@Data
public class EnfeoffAccountCommand {

  private Long matchFACapitalDetailId;

  private Double assignedCash;

}

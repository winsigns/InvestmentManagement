package com.winsigns.investment.inventoryService.command;

import lombok.Getter;
import lombok.Setter;

/**
 * 如果
 * 
 * @author yimingjin
 *
 */
public class CreateCapitalDetailCommand extends CreateFundAccountCapitalPoolCommand {

  @Getter
  @Setter
  private Long faCapitalPoolId;

  @Getter
  @Setter
  private Long ecaCashPoolId;

}

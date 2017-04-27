package com.winsigns.investment.tradeService.command;

import lombok.Data;

@Data
public class CreateDoneCommand {

  // 委托ID
  private Long entrustId;

  // 成交价格
  private Double donePrice;

  // 成交数量
  private Long doneQuantity;
}

package com.winsigns.investment.inventoryService.command;

import lombok.Data;

@Data
public class ApplyInventoryCommand {
  // 指令序号
  private Long instructionId;

  // 投资组合
  private Long portfolioId;

  // 投资标的
  private Long securityId;

  // 交易服务
  private String tradeSvc;

  // 币种
  private String currency;

  // 申请的资金
  private Double applidCapital;

  // 申请的持仓
  private Integer applidPosition;
}

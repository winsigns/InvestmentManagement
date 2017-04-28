package com.winsigns.investment.inventoryService.command;

import com.winsigns.investment.framework.constant.CurrencyCode;

import lombok.Data;

@Data
public class ApplyResourceCommand {

  // 虚拟成交编号
  private Long virtualDoneId;

  // 指令ID
  private Long instructionId;

  // 投资组合
  private Long portfolioId;

  // 投资标的
  private Long securityId;

  // 币种
  private CurrencyCode currency;

  // 资金服务
  private String capitalService;

  // 申请的资金
  private Double appliedCapital = 0.0;

  // 持仓服务
  private String positionService;

  // 申请的持仓
  private Long appliedPosition = 0L;

  // 操作序号
  private String operatorSequence;

  // 记录下请求的语言
  private String language;
}

package com.winsigns.investment.tradeService.command;

import com.winsigns.investment.tradeService.constant.CurrencyCode;

import lombok.Data;

@Data
public class ApplyResourceCommand {

  // 投资组合
  private Long portfolioId;

  // 投资标的
  private Long securityId;

  // 币种
  private CurrencyCode currency;

  // 资金服务
  private String capitalService;

  // 持仓服务
  private String positionService;

  // 申请的资金
  private Double appliedCapital = 0.0;

  // 申请的持仓
  private Long appliedPosition = 0L;

  // 操作序号
  private String operatorSequence;

}

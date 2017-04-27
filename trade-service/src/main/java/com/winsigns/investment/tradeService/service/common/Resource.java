package com.winsigns.investment.tradeService.service.common;

import lombok.Data;
import lombok.NonNull;

@Data
public class Resource {
  // 交易服务
  final private @NonNull ITradeService service;

  // 申请的资金
  private Double appliedCapital;

  // 申请的持仓
  private Long appliedPosition;

  public Resource(ITradeService service) {
    this.service = service;
  }
}

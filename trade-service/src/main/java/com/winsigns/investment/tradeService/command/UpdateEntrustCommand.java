package com.winsigns.investment.tradeService.command;

import lombok.Data;

@Data
public class UpdateEntrustCommand {
  // 委托ID
  private Long entrustId;

  // 交易服务
  private String tradeService;

  // 交易方向
  private String tradeType;

  // 经济公司
  private Long brokerageFirmId;

  // 委托类型
  private String priceType;

  // 委托价格
  private Double entrustPrice;

  // 委托数量
  private Long entrustQuantity;
}

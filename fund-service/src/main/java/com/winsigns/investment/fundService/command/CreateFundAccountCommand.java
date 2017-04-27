package com.winsigns.investment.fundService.command;

import lombok.Data;

@Data
public class CreateFundAccountCommand {

  // 所属基金产品
  private Long fundId;

  // 名称
  private String name;

  // 投资经理
  private Long investManagerId;

}

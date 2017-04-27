package com.winsigns.investment.fundService.command;

import lombok.Data;

@Data
public class UpdateFundAccountCommand {

  // 名称
  private String name;

  // 投资经理
  private Long investManagerId;

}

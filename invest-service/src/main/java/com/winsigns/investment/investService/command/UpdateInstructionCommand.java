package com.winsigns.investment.investService.command;

import com.winsigns.investment.framework.constant.CurrencyCode;
import com.winsigns.investment.investService.constant.InstructionVolumeType;

import lombok.Data;

/**
 * 更新指令的命令
 * 
 * @author yimingjin
 *
 */
@Data
public class UpdateInstructionCommand {
  // 投资组合
  private Long portfolioId;

  // 投资标的
  private Long securityId;

  // 投资服务
  private String investService;

  // 投资类型
  private String investType;

  // 币种
  private CurrencyCode currency;

  // 成本价
  private Double costPrice;

  // 数量类型
  private InstructionVolumeType volumeType;

  // 指令数量
  private Long quantity;

  // 指令金额
  private Double amount;
}

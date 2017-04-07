package com.winsigns.investment.investService.command;

import lombok.Data;

/**
 * 创建指令的命令
 * 
 * @author yimingjin
 *
 */
@Data
public class CreateInstructionCommand {
  // 投资经理
  private Long investManagerId;

}

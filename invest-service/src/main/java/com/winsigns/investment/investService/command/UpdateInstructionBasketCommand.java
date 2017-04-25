package com.winsigns.investment.investService.command;

import lombok.Getter;
import lombok.Setter;

/**
 * 更新指令篮子的命令
 * 
 * @author yimingjin
 *
 */
public class UpdateInstructionBasketCommand extends UpdateInstructionCommand {

  @Getter
  @Setter
  private String basketName;
}

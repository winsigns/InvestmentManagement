package com.winsigns.investment.investService.command;

import lombok.Getter;
import lombok.Setter;

/**
 * 创建指令篮子的命令
 * 
 * @author yimingjin
 *
 */
public class CreateInstructionBasketCommand extends CreateInstructionCommand {
  @Getter
  @Setter
  private String basketName;
}

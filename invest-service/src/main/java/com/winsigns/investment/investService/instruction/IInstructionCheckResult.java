package com.winsigns.investment.investService.instruction;

import com.winsigns.investment.investService.constant.InstructionMessageCode;
import com.winsigns.investment.investService.constant.InstructionMessageType;

public interface IInstructionCheckResult {

  // 消息类型
  public InstructionMessageType getMessageType();

  // 消息代码
  public InstructionMessageCode getMessageCode();
}

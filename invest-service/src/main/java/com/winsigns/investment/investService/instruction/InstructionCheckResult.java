package com.winsigns.investment.investService.instruction;

import com.winsigns.investment.investService.constant.InstructionMessageCode;
import com.winsigns.investment.investService.constant.InstructionMessageType;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InstructionCheckResult implements IInstructionCheckResult {

  private InstructionMessageType messageType;

  private InstructionMessageCode messageCode;

  @Override
  public InstructionMessageType getMessageType() {
    return this.messageType;
  }

  @Override
  public InstructionMessageCode getMessageCode() {
    return this.messageCode;
  }

}

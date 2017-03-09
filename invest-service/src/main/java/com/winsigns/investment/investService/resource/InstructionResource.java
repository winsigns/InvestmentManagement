package com.winsigns.investment.investService.resource;

import com.winsigns.investment.framework.hal.HALResponse;
import com.winsigns.investment.investService.model.Instruction;

public class InstructionResource extends HALResponse<Instruction> {

  public InstructionResource(Instruction instruction) {
    super(instruction);
  }

}

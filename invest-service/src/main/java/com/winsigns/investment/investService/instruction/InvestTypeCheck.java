package com.winsigns.investment.investService.instruction;

import org.springframework.stereotype.Component;

import com.winsigns.investment.investService.constant.InstructionMessageCode;
import com.winsigns.investment.investService.constant.InstructionMessageType;
import com.winsigns.investment.investService.model.Instruction;

@Component
public class InvestTypeCheck extends InstructionCheck {

  @Override
  public String getField() {
    return "investType";
  }

  @Override
  public IInstructionCheckResult check(Instruction thisInstruction) {

    if (thisInstruction.getInvestType() == null) {
      return new InstructionCheckResult(InstructionMessageType.ERROR,
          InstructionMessageCode.INVEST_TYPE_CANNOT_NULL);
    }

    return null;
  }

}

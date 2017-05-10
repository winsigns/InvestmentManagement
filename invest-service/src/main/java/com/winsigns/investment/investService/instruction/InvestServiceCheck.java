package com.winsigns.investment.investService.instruction;

import org.springframework.stereotype.Component;

import com.winsigns.investment.investService.constant.InstructionMessageCode;
import com.winsigns.investment.investService.constant.InstructionMessageType;
import com.winsigns.investment.investService.model.Instruction;

@Component
public class InvestServiceCheck extends InstructionCheck {

  @Override
  public String getField() {
    return "investService";
  }

  @Override
  public IInstructionCheckResult check(Instruction thisInstruction) {
    if (thisInstruction.getInvestService() == null) {
      return new InstructionCheckResult(InstructionMessageType.ERROR,
          InstructionMessageCode.INVEST_SERVICE_CANNOT_NULL);
    }
    return null;
  }

}

package com.winsigns.investment.investService.instruction;

import org.springframework.stereotype.Component;

import com.winsigns.investment.investService.constant.InstructionMessageCode;
import com.winsigns.investment.investService.constant.InstructionMessageType;
import com.winsigns.investment.investService.model.Instruction;

@Component
public class SecurityCheck extends InstructionCheck {

  @Override
  public String getField() {
    return "securityId";
  }

  @Override
  public IInstructionCheckResult check(Instruction thisInstruction) {
    if (thisInstruction.getSecurityId() == null) {
      return new InstructionCheckResult(InstructionMessageType.ERROR,
          InstructionMessageCode.INVEST_SECURITY_CANNOT_NULL);
    }
    return null;
  }

}

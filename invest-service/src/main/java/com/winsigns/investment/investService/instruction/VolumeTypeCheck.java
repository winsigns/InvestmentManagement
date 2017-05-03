package com.winsigns.investment.investService.instruction;

import org.springframework.stereotype.Component;

import com.winsigns.investment.investService.constant.InstructionMessageCode;
import com.winsigns.investment.investService.constant.InstructionMessageType;
import com.winsigns.investment.investService.constant.InstructionVolumeType;
import com.winsigns.investment.investService.model.Instruction;

@Component
public class VolumeTypeCheck extends InstructionCheck {

  @Override
  public String getField() {
    return "volumeType";
  }

  @Override
  public IInstructionCheckResult check(Instruction thisInstruction) {

    if (!InstructionVolumeType.contains(thisInstruction.getVolumeType())) {
      return new InstructionCheckResult(InstructionMessageType.ERROR,
          InstructionMessageCode.INSTRUCTION_VOLUME_TYPE_NOT_SUPPORT);
    }
    return null;

  }

}

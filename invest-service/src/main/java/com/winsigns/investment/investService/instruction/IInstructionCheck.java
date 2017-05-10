package com.winsigns.investment.investService.instruction;

import com.winsigns.investment.investService.model.Instruction;

public interface IInstructionCheck {

  public String getField();

  /**
   * 检查一个指令
   * 
   * @param thisInstruction
   * @return 如果返回null，则表示正常
   */
  public IInstructionCheckResult check(Instruction thisInstruction);

}

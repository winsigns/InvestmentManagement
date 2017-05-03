package com.winsigns.investment.investService.instruction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winsigns.investment.investService.constant.InstructionMessageCode;
import com.winsigns.investment.investService.constant.InstructionMessageType;
import com.winsigns.investment.investService.integration.FundServiceIntegration;
import com.winsigns.investment.investService.model.Instruction;

@Component
public class PortfolioCheck extends InstructionCheck {

  @Autowired
  FundServiceIntegration fundService;

  @Override
  public String getField() {
    return "portfolioId";
  }

  @Override
  public IInstructionCheckResult check(Instruction thisInstruction) {

    Long portfolioId = thisInstruction.getPortfolioId();

    if (portfolioId == null) {
      return new InstructionCheckResult(InstructionMessageType.ERROR,
          InstructionMessageCode.PORTFOLIO_NOT_NULL);
    } else {
      // 检查该投资组合是否为该投资经理管理
      Long investManagerId = fundService.getPortfolioInvestManager(portfolioId);
      if (investManagerId == null
          || !investManagerId.equals(thisInstruction.getInvestManagerId())) {
        return new InstructionCheckResult(InstructionMessageType.ERROR,
            InstructionMessageCode.PORTFOLIO_NOT_MATCHED_INVESTMANAGER);
      }
    }
    return null;
  }

}

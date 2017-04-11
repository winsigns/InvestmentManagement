package com.winsigns.investment.tradeService.service.stock.SSEAStock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winsigns.investment.tradeService.command.CommitInstructionCommand;
import com.winsigns.investment.tradeService.service.common.AbstractTradeService;
import com.winsigns.investment.tradeService.service.common.ITradeType;
import com.winsigns.investment.tradeService.service.common.MockInvestService;
import com.winsigns.investment.tradeService.service.stock.StockInvestService;
import com.winsigns.investment.tradeService.service.stock.StockTradeType;

@Service
public class SSEAStockTradeService extends AbstractTradeService {

  @Autowired
  StockInvestService investService;

  @Override
  public MockInvestService getSupportedInvestService() {
    return investService;
  }

  @Override
  public String getSupportedSecurity() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ITradeType[] getTradeType() {
    return StockTradeType.values();
  }

  @Override
  public Double calculateRequiredCapital(CommitInstructionCommand command) {
    Double requiredCapital = 0.0;

    if (this.getTradeType(command.getInvestType()).equals(StockTradeType.BUY)) {
      Double costPrice = 0.0;
      // TODO 不限价需要获取价格
      if (command.getCostPrice() == null) {

      } else {
        costPrice = command.getCostPrice();
      }

      switch (command.getVolumeType()) {
        case AmountType:
          requiredCapital = command.getAmount();
          break;
        case FixedType:
          requiredCapital = costPrice * command.getQuantity();
          break;
        default:
          requiredCapital = costPrice * command.getQuantity();
          break;
      }
    }
    return requiredCapital;
  }

  @Override
  public Long calculateRequiredPosition(CommitInstructionCommand command) {
    Long requiredPosition = 0L;

    if (this.getTradeType(command.getInvestType()).equals(StockTradeType.SELL)) {

      Double costPrice = 0.0;
      // TODO 不限价需要获取价格
      if (command.getCostPrice() == null) {

      } else {
        costPrice = command.getCostPrice();
      }

      switch (command.getVolumeType()) {
        case AmountType:
          requiredPosition = (long) Math.floor(command.getAmount() / costPrice);
          break;
        case FixedType:
          requiredPosition = command.getQuantity();
          break;
        default:
          requiredPosition = command.getQuantity();
          break;
      }
    }

    return requiredPosition;
  }
}

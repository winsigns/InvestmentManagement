package com.winsigns.investment.tradeService.service.stock.SSEAStock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winsigns.investment.tradeService.command.CommitInstructionCommand;
import com.winsigns.investment.tradeService.constant.CurrencyCode;
import com.winsigns.investment.tradeService.model.Done;
import com.winsigns.investment.tradeService.service.common.AbstractTradeService;
import com.winsigns.investment.tradeService.service.common.IPriceType;
import com.winsigns.investment.tradeService.service.common.ITradeType;
import com.winsigns.investment.tradeService.service.common.RemoteCapitalService;
import com.winsigns.investment.tradeService.service.common.RemoteInvestService;
import com.winsigns.investment.tradeService.service.common.RemotePositionService;
import com.winsigns.investment.tradeService.service.common.Resource;
import com.winsigns.investment.tradeService.service.stock.StockInvestService;
import com.winsigns.investment.tradeService.service.stock.StockTradeType;

@Service
public class SSEAStockTradeService extends AbstractTradeService {

  @Autowired
  StockInvestService investService;

  @Override
  public RemoteInvestService getSupportedInvestService() {
    return investService;
  }

  @Override
  public RemoteCapitalService getUsedCapitalService() {
    return new RemoteCapitalService("CHINA_GENERAL_CAPITAL", CurrencyCode.CNY);
  }

  @Override
  public RemotePositionService getUsedPositionService() {
    return new RemotePositionService("SSEAStockPositionService");
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
  public IPriceType[] getPriceType() {
    return SSEAStockPriceType.values();
  }

  @Override
  public Resource calculateRequiredResource(CommitInstructionCommand command) {

    Resource resouce = new Resource(this);

    Double costPrice = 0.0;
    // TODO 不限价需要获取价格
    if (command.getCostPrice() == null) {

    } else {
      costPrice = command.getCostPrice();
    }

    if (this.getTradeType(command.getInvestType()).equals(StockTradeType.BUY)) {

      Double capital = 0.0;

      switch (command.getVolumeType()) {
        case AmountType:
          capital = command.getAmount();
          break;
        case FixedType:
          capital = costPrice * command.getQuantity();
          break;
        default:
          capital = costPrice * command.getQuantity();
          break;
      }
      resouce.setAppliedCapital(capital);
    } else {
      Long position;
      switch (command.getVolumeType()) {
        case AmountType:
          position = (long) Math.floor(command.getAmount() / costPrice);
          break;
        case FixedType:
          position = command.getQuantity();
          break;
        default:
          position = command.getQuantity();
          break;
      }
      resouce.setAppliedPosition(position);
    }
    return resouce;
  }

  @Override
  public void done(Done thisDone) {

  }

}

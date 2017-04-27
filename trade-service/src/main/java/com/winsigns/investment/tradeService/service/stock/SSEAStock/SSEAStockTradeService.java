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
    return new RemoteCapitalService("GeneralCapitalService", CurrencyCode.CNY);
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
    Long position = 0L;
    Double capital = 0.0;
    Double costPrice = 0.0;
    // TODO 不限价需要获取价格 公允价->最新价->昨收盘价
    if (command.getCostPrice() == null) {

    } else {
      costPrice = command.getCostPrice();
    }

    if (this.getTradeType(command.getInvestType()).equals(StockTradeType.BUY)) {

      switch (command.getVolumeType()) {
        case AmountType:
          capital = -command.getAmount();
          position = (long) Math.floor(capital / costPrice);
          break;
        case FixedType:
          capital = -costPrice * command.getQuantity();
          position = command.getQuantity();
          break;
        default:
          capital = -costPrice * command.getQuantity();
          position = command.getQuantity();
          break;
      }
    } else {
      switch (command.getVolumeType()) {
        case AmountType:
          capital = command.getAmount();
          position = -(long) Math.floor(command.getAmount() / costPrice);
          break;
        case FixedType:
          capital = command.getQuantity() * costPrice;
          position = -command.getQuantity();
          break;
        default:
          capital = command.getQuantity() * costPrice;
          position = -command.getQuantity();
          break;
      }
    }
    resouce.setAppliedCapital(capital);
    resouce.setAppliedPosition(position);
    return resouce;
  }

  @Override
  public void done(Done thisDone) {

  }

}

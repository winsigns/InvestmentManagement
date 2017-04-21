package com.winsigns.investment.inventoryService.position.stock.sseAStock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winsigns.investment.inventoryService.exception.ResourceApplicationExcepiton;
import com.winsigns.investment.inventoryService.position.common.AbstractPositionService;
import com.winsigns.investment.inventoryService.position.stock.StockPosition;
import com.winsigns.investment.inventoryService.position.stock.StockPositionRepository;


@Service
public class SSEAStockPositionService extends AbstractPositionService {

  public enum ErrorCode {
    // 卖出未找到对应的持仓资源
    NOT_FIND_POSITION_RESOURCE,
    // 可卖持仓量不足
    CAN_SELL_POSITION_NOT_ENOUGH;

    public String toString() {
      return "SSEAStockPositionService:" + this.name();
    }
  }

  @Autowired
  StockPositionRepository stockPositionRepository;

  @Override
  public void apply(Long portfolioId, Long securityId, String type, Long quantity)
      throws ResourceApplicationExcepiton {

    StockPosition position = stockPositionRepository
        .findByPortfolioIdAndSecurityIdAndExternalTradeAccountIdIsNull(portfolioId, securityId);

    if (quantity.longValue() >= 0) {// 增加持仓
      if (position == null) { // 新建一个持仓
        position = new StockPosition();
        position.setPortfolioId(portfolioId);
        position.setSecurityId(securityId);

        position.changePositionQuantity(quantity);
      } else {
        position.changePositionQuantity(quantity);
      }
      position = stockPositionRepository.save(position);
    } else {// 减少持仓
      if (position == null) {
        throw new ResourceApplicationExcepiton(ErrorCode.NOT_FIND_POSITION_RESOURCE.toString());
      } else {
        position.changeCanSellPositionQuantity(quantity);
        position.changePositionQuantity(quantity);
        if (position.getCanSellPositionQuantity().longValue() < 0) {
          throw new ResourceApplicationExcepiton(ErrorCode.CAN_SELL_POSITION_NOT_ENOUGH.toString());
        }
        position = stockPositionRepository.save(position);
      }
    }
  }
}

package com.winsigns.investment.inventoryService.position.stock.sseAStock;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winsigns.investment.inventoryService.exception.ResourceApplicationExcepiton;
import com.winsigns.investment.inventoryService.model.FloatPositionSerial;
import com.winsigns.investment.inventoryService.model.PositionSerial;
import com.winsigns.investment.inventoryService.position.common.AbstractPositionService;
import com.winsigns.investment.inventoryService.position.stock.StockPosition;
import com.winsigns.investment.inventoryService.position.stock.StockPositionRepository;
import com.winsigns.investment.inventoryService.repository.PositionSerialRepository;
import com.winsigns.investment.inventoryService.service.PositionSerialService;

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

  @Autowired
  PositionSerialRepository positionSerialRepository;

  @Autowired
  PositionSerialService positionSerialService;

  @Override
  public List<PositionSerial> apply(Long portfolioId, Long securityId, String type,
      Long appliedPosition) throws ResourceApplicationExcepiton {

    List<PositionSerial> serials = new ArrayList<PositionSerial>();
    List<StockPosition> positions = stockPositionRepository
        .findByPortfolioIdAndSecurityIdOrderByCanSellPositionQuantityDesc(portfolioId, securityId);
    if (appliedPosition.longValue() >= 0) {// 增加持仓
      StockPosition position = null;
      if (positions == null || positions.isEmpty()) {
        // 增加一条全新的持仓，因为是浮动指标，外部交易账户id设置为null
        position = new StockPosition();
        position.setPortfolioId(portfolioId);
        position.setSecurityId(securityId);

        position.changeFloatPositionQuantity(appliedPosition);
        position = stockPositionRepository.save(position);

      } else { // 增加最少的持仓
        position = positions.get(positions.size() - 1);
        position.changeFloatPositionQuantity(appliedPosition);
        position = stockPositionRepository.save(position);
      }
      PositionSerial serial = positionSerialService.addPositionSerial(FloatPositionSerial.class,
          portfolioId, securityId, type, position.getExternalTradeAccountId(), appliedPosition);
      serials.add(serial);

    } else {
      if (positions == null || positions.isEmpty()) {
        throw new ResourceApplicationExcepiton(ErrorCode.NOT_FIND_POSITION_RESOURCE.toString());
      }
      appliedPosition = Math.abs(appliedPosition);
      boolean isEnded = false;
      for (StockPosition position : positions) {
        Long thisQuantity = 0L;
        Long currRemain = position.getCanSellPositionQuantity() - appliedPosition;
        if (currRemain.longValue() >= 0) { // 当前持仓记录有剩余，则表示已经分配完
          thisQuantity = appliedPosition;
          isEnded = true;
        } else {
          thisQuantity = position.getCanSellPositionQuantity();
        }
        if (thisQuantity.longValue() > 0) {
          appliedPosition -= thisQuantity;
          position.changeFloatCanSellPositionQuantity(-thisQuantity);
          position.changeFloatPositionQuantity(-thisQuantity);
          position = stockPositionRepository.save(position);

          PositionSerial serial = positionSerialService.addPositionSerial(FloatPositionSerial.class,
              portfolioId, securityId, type, position.getExternalTradeAccountId(), appliedPosition);
          serials.add(serial);
        }
        if (isEnded) {
          break;
        }
      }
      // 如果最后还有剩余，则抛异常
      if (appliedPosition.longValue() > 0) {
        throw new ResourceApplicationExcepiton(ErrorCode.CAN_SELL_POSITION_NOT_ENOUGH.toString());
      }
    }

    return serials;
  }
}

package com.winsigns.investment.tradeService.service.stock;

import org.springframework.stereotype.Component;

import com.winsigns.investment.tradeService.service.common.RemoteInvestService;

/**
 * 模拟的现货投资服务
 * 
 * @author yimingjin
 *
 */
@Component
public class StockInvestService extends RemoteInvestService {

  @Override
  public StockInvestType[] getInstructionType() {
    return StockInvestType.values();
  }
}

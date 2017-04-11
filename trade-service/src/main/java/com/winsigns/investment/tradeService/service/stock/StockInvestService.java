package com.winsigns.investment.tradeService.service.stock;

import org.springframework.stereotype.Component;

import com.winsigns.investment.tradeService.service.common.MockInvestService;

/**
 * 模拟的现货投资服务
 * 
 * @author yimingjin
 *
 */
@Component
public class StockInvestService extends MockInvestService {

  @Override
  public StockInvestType[] getInstructionType() {
    return StockInvestType.values();
  }
}

package com.winsigns.investment.investService.service.stockInvestService;

import org.springframework.stereotype.Service;

import com.winsigns.investment.investService.service.common.AbstractInvestService;

/**
 * 现货投资服务
 * 
 * @author yimingjin
 *
 */
@Service
public class StockInvestService extends AbstractInvestService {

  @Override
  public StockInvestType[] getInvestType() {
    return StockInvestType.values();
  }

}

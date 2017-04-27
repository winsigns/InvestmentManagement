package com.winsigns.investment.investService.service.futureInvestService;

import org.springframework.stereotype.Service;

import com.winsigns.investment.investService.service.common.AbstractInvestService;

/**
 * 期货投资服务
 * 
 * @author yimingjin
 *
 */
@Service
public class FutureInvestService extends AbstractInvestService {

  @Override
  public FutureInvestType[] getInvestType() {
    return FutureInvestType.values();
  }

}

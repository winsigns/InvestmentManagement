package com.winsigns.investment.tradeService.service.common;

/**
 * 模拟投资服务
 * 
 * @author yimingjin
 *
 */
public abstract class RemoteInvestService {

  public String getName() {
    return this.getClass().getSimpleName();
  }

  public abstract IMockInvestType[] getInstructionType();

}

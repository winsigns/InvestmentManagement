package com.winsigns.investment.tradeService.service.common;

import com.winsigns.investment.tradeService.command.CommitInstructionCommand;

/**
 * 交易服务的接口
 * 
 * @author yimingjin
 *
 */
public interface ITradeService {

  /**
   *
   * @return 服务名称
   */
  String getName();

  /**
   * 
   * @return 服务简称
   */
  String getSimpleName();

  /**
   * 
   * @return 所支持的投资服务
   */
  MockInvestService getSupportedInvestService();


  /**
   * 投资标的
   * 
   * @return
   */
  // TODO
  String getSupportedSecurity();

  /**
   * 
   * @return 交易类型
   */
  ITradeType[] getTradeType();

  /**
   * 获取指定名字的交易类型
   * 
   * @param name 名字
   * @return
   */
  ITradeType getTradeType(String name);

  /**
   * 计算指令需要的资金
   * 
   * @param command
   * @return
   */
  Double calculateRequiredCapital(CommitInstructionCommand command);

  /**
   * 计算指令需要的持仓
   * 
   * @param command
   * @return
   */
  Long calculateRequiredPosition(CommitInstructionCommand command);
}

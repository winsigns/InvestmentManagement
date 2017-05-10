package com.winsigns.investment.tradeService.service.common;

import com.winsigns.investment.tradeService.command.CommitInstructionCommand;
import com.winsigns.investment.tradeService.exception.SendResourceApplicationFailed;
import com.winsigns.investment.tradeService.model.Done;

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
  public String getName();

  /**
   * 
   * @return 服务简称
   */
  public String getSimpleName();

  /**
   * 
   * @return 所支持的投资服务
   */
  public RemoteInvestService getSupportedInvestService();

  /**
   * 
   * @return 所使用的资金服务
   */
  public RemoteCapitalService getUsedCapitalService();

  /**
   * 
   * @return 所使用的持仓服务
   */
  public RemotePositionService getUsedPositionService();

  /**
   * 投资标的
   * 
   * @return
   */
  // TODO
  public String getSupportedSecurity();

  /**
   * 
   * @return 交易类型
   */
  public ITradeType[] getTradeType();

  /**
   * 获取指定名字的交易类型
   * 
   * @param name 名字
   * @return
   */
  public ITradeType getTradeType(String name);

  /**
   * 价格类型
   * 
   * @return
   */
  public IPriceType[] getPriceType();

  /**
   * 价格类型
   * 
   * @return
   */
  public IPriceType getPriceType(String name);

  /**
   * 计算指令需要的资源
   * 
   * @param command
   * @return
   */
  public Resource calculateRequiredResource(CommitInstructionCommand command);

  /**
   * 虚拟成交
   */
  public void virtualDone(CommitInstructionCommand command, Resource resource)
      throws SendResourceApplicationFailed;

  /**
   * 成交信息
   * 
   * @param thisDone
   */
  public void done(Done thisDone);

}

package com.winsigns.investment.investService.service.common;

import com.winsigns.investment.investService.exception.InvestCommitFailedExcepiton;
import com.winsigns.investment.investService.model.Instruction;

/**
 * 投资服务的接口
 * 
 * @author yimingjin
 *
 */
public interface IInvestService {

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
   * @return 投资类型
   */
  IInvestType[] getInvestType();

  /**
   * 获取指定名字的投资类型
   * 
   * @param name 名字
   * @return
   */
  IInvestType getInvestType(String name);

  /**
   * 提交指令
   * 
   * @param instruction
   */
  void commitInstruction(Instruction instruction) throws InvestCommitFailedExcepiton;
}

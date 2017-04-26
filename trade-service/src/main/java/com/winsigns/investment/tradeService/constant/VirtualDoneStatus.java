package com.winsigns.investment.tradeService.constant;


/**
 * 虚拟成交的状态
 * 
 * @author yimingjin
 *
 */
public enum VirtualDoneStatus {

  // 初始状态
  INIT,

  // 处理状态
  PROCESSING,

  // 完成状态
  FINISHED,

  // 作废状态
  CANCELED;
}

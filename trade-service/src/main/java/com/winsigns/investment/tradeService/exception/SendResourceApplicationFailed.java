package com.winsigns.investment.tradeService.exception;

import com.winsigns.investment.framework.exception.CommonException;

/**
 * 发送资源申请失败
 * 
 * @author yimingjin
 *
 */
public class SendResourceApplicationFailed extends CommonException {

  /**
   * 
   */
  private static final long serialVersionUID = 5290249649753402117L;

  public SendResourceApplicationFailed() {

  }

  public SendResourceApplicationFailed(CommonException e) {
    super(e);
  }

  public SendResourceApplicationFailed(String e) {
    super(e);
  }
}

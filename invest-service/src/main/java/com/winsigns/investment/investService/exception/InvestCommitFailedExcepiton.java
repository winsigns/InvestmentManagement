package com.winsigns.investment.investService.exception;

import com.winsigns.investment.framework.exception.CommonException;

public class InvestCommitFailedExcepiton extends CommonException {
  /**
   * 
   */
  private static final long serialVersionUID = -6038447188657669743L;

  public InvestCommitFailedExcepiton() {

  }

  public InvestCommitFailedExcepiton(CommonException e) {
    super(e);
  }

  public InvestCommitFailedExcepiton(String e) {
    super(e);
  }


}

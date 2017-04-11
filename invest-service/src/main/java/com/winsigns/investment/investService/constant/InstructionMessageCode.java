package com.winsigns.investment.investService.constant;

import com.winsigns.investment.framework.i18n.i18nHelper;

/**
 * 指令的错误
 * 
 * @author yimingjin
 *
 */
public enum InstructionMessageCode {

  // 投资组合不能为空
  PORTFOLIO_NOT_NULL,

  // 该投资组合不属于该投资经理
  PORTFOLIO_NOT_MATCHED_INVESTMANAGER,

  // 投资标的不能为空
  INVEST_SECURITY_CANNOT_NULL,

  // 投资服务不能为空
  INVEST_SERVICE_CANNOT_NULL,

  // 投资类型不能为空
  INVEST_TYPE_CANNOT_NULL,

  // 指令操作不支持
  INSTRUCTION_OPERATOR_NOT_SUPPORT,

  // 指令提交失败
  INSTRUCTION_COMMIT_FAIL;
  /**
   * 国际化
   * 
   * @return
   */
  public String i18n() {
    return i18nHelper.i18n(this);
  }
}

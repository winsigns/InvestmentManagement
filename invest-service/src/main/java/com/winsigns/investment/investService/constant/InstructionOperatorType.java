package com.winsigns.investment.investService.constant;

import com.winsigns.investment.framework.i18n.i18nHelper;

public enum InstructionOperatorType {

  // 修改
  MODIFY,

  // 提交
  COMMIT,

  // 撤回
  RECALL,

  // 删除
  DELETE;

  /**
   * 国际化
   * 
   * @return
   */
  public String i18n() {
    return i18nHelper.i18n(this);
  }
}

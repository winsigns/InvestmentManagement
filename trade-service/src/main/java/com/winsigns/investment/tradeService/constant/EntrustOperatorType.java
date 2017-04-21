package com.winsigns.investment.tradeService.constant;

import com.winsigns.investment.framework.i18n.i18nHelper;

public enum EntrustOperatorType {

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

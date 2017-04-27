package com.winsigns.investment.tradeService.constant;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.winsigns.investment.framework.i18n.i18nHelper;

/**
 * 委托的状态机
 * 
 * @author yimingjin
 *
 */
public enum EntrustStatus {
  // 草稿
  DRAFT,

  // 提交中
  COMMITING,

  // 交易中
  TRADING,

  // 提交失败
  COMMIT_FAILED,

  // 撤单中
  RECALLING,

  // 全部撤单
  ALL_RECALLED,

  // 部分撤单部分成交
  PART_RECALLED_PART_DONE,

  // 全部成交
  ALL_DONE,

  // 已删除
  DELETED;

  // 支持的操作
  private static HashMap<EntrustStatus, List<EntrustOperatorType>> supportOperatorTypes =
      new HashMap<EntrustStatus, List<EntrustOperatorType>>();
  static {
    supportOperatorTypes.put(DRAFT,
        asList(EntrustOperatorType.MODIFY, EntrustOperatorType.DELETE, EntrustOperatorType.COMMIT));
    supportOperatorTypes.put(COMMITING,
        asList(EntrustOperatorType.MODIFY, EntrustOperatorType.RECALL));
    supportOperatorTypes.put(DELETED, new ArrayList<EntrustOperatorType>());
  }

  /**
   * 返回指定状态的支持的操作、
   * 
   * @param entrustStatus 指定的状态
   * @return 返回支持的操作列表
   */
  public static List<EntrustOperatorType> getSupportedOperator(EntrustStatus entrustStatus) {
    return entrustStatus.getSupportedOperator();
  }

  /**
   * 获取当前状态的支持的操作
   * 
   * @return 返回支持的操作列表
   */
  public List<EntrustOperatorType> getSupportedOperator() {
    return supportOperatorTypes.get(this);
  }

  /**
   * 判断该状态是否支持该操作
   * 
   * @param thisType 指定的操作类型
   * @return
   */
  public boolean isSupportedOperator(EntrustOperatorType thisType) {
    for (EntrustOperatorType type : getSupportedOperator()) {
      if (type.equals(thisType)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 国际化
   * 
   * @return
   */
  public String i18n() {
    return i18nHelper.i18n(this);
  }
}

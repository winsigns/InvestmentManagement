package com.winsigns.investment.investService.constant;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.winsigns.investment.framework.i18n.i18nHelper;

/**
 * 指令的状态机
 * 
 * @author yimingjin
 *
 */
public enum InstructionStatus {
  // 草稿
  DRAFT,

  // 提交中
  COMMITING,

  // 风控中
  RISKING,

  // 审批中
  APPROVING,

  // 指令分配中
  ASSIGNING,

  // 交易中
  TRADING,

  // 撤回中
  RECALLING,

  // 完成
  ACCOMPLISHED,

  // 已删除
  DELETED;

  // 支持的操作
  private static HashMap<InstructionStatus, List<InstructionOperatorType>> supportOperatorTypes =
      new HashMap<InstructionStatus, List<InstructionOperatorType>>();
  static {
    supportOperatorTypes.put(DRAFT, asList(InstructionOperatorType.MODIFY,
        InstructionOperatorType.DELETE, InstructionOperatorType.COMMIT));
    supportOperatorTypes.put(COMMITING,
        asList(InstructionOperatorType.MODIFY, InstructionOperatorType.RECALL));
    supportOperatorTypes.put(DELETED, new ArrayList<InstructionOperatorType>());
  }

  /**
   * 返回指定状态的支持的操作、
   * 
   * @param instructionStatus 指定的状态
   * @return 返回支持的操作列表
   */
  public static List<InstructionOperatorType> getSupportedOperator(
      InstructionStatus instructionStatus) {
    return instructionStatus.getSupportedOperator();
  }

  /**
   * 获取当前状态的支持的操作
   * 
   * @return 返回支持的操作列表
   */
  public List<InstructionOperatorType> getSupportedOperator() {
    return supportOperatorTypes.get(this);
  }

  /**
   * 判断该状态是否支持该操作
   * 
   * @param thisType 指定的操作类型
   * @return
   */
  public boolean isSupportedOperator(InstructionOperatorType thisType) {
    for (InstructionOperatorType type : getSupportedOperator()) {
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
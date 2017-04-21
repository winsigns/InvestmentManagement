package com.winsigns.investment.investService.constant;

public enum InstructionVolumeType {

  FixedType, // 固定数量类型
  AmountType; // 金额数量类型

  public static boolean contains(InstructionVolumeType type) {
    for (InstructionVolumeType typeEnum : InstructionVolumeType.values()) {
      if (typeEnum.equals(type)) {
        return true;
      }
    }
    return false;
  }

}

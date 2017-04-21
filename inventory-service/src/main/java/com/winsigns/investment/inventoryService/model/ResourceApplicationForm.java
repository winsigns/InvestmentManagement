package com.winsigns.investment.inventoryService.model;

import java.util.Date;

import com.winsigns.investment.inventoryService.command.ApplyResourceCommand;

import lombok.Getter;
import lombok.Setter;

public class ResourceApplicationForm extends ApplyResourceCommand implements Cloneable {

  // 申请时间
  @Getter
  @Setter
  Date appliedTime;

  public static enum ApplyStatus {
    // 初始状态
    INIT,
    // 处理中
    PROCESSING
  }

  @Getter
  @Setter
  ApplyStatus status = ApplyStatus.INIT;

  public ResourceApplicationForm clone() {
    ResourceApplicationForm o = null;
    try {
      o = (ResourceApplicationForm) super.clone();
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }
    return o;
  }
}

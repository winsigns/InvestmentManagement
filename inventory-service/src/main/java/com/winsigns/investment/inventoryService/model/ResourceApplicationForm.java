package com.winsigns.investment.inventoryService.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.hateoas.core.Relation;

import com.winsigns.investment.framework.model.AbstractEntity;
import com.winsigns.investment.inventoryService.constant.CurrencyCode;

import lombok.Getter;
import lombok.Setter;

@Entity
@Relation(value = "resource-form", collectionRelation = "resource-forms")
public class ResourceApplicationForm extends AbstractEntity implements Cloneable {

  // 虚拟成交编号
  @Getter
  @Setter
  private Long virtualDoneId;

  // 指令ID
  @Getter
  @Setter
  private Long instructionId;

  // 投资组合
  @Getter
  @Setter
  private Long portfolioId;

  // 投资标的
  @Getter
  @Setter
  private Long securityId;

  // 币种
  @Getter
  @Setter
  private CurrencyCode currency;

  // 资金服务
  @Getter
  @Setter
  private String capitalService;

  // 申请的资金
  @Getter
  @Setter
  private Double appliedCapital = 0.0;

  // 持仓服务
  @Getter
  @Setter
  private String positionService;

  // 申请的持仓
  @Getter
  @Setter
  private Long appliedPosition = 0L;

  // 操作序号
  @Getter
  @Setter
  private String operatorSequence;

  // 申请时间
  @Getter
  Date appliedTime = new Timestamp(System.currentTimeMillis());

  public static enum ApplyStatus {
    // 初始状态
    INIT,
    // 处理中
    PROCESSING
  }

  @Getter
  @Setter
  @Enumerated(EnumType.STRING)
  private ApplyStatus status = ApplyStatus.INIT;

  @Getter
  @Setter
  private String language;

  @Getter
  @Setter
  private String message;

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

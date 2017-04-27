package com.winsigns.investment.inventoryService.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.winsigns.investment.framework.model.OperatorEntity;
import com.winsigns.investment.inventoryService.constant.CurrencyCode;

import lombok.Getter;
import lombok.Setter;

/**
 * 资金流水的基类
 * <p>
 * 源账户<br>
 * 对手账户<br>
 * 发生金额<br>
 * 关联流水号<br>
 * 操作时间<br>
 * 
 * @author yimingjin
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
public abstract class CapitalSerial extends OperatorEntity {

  // 源账户
  @Getter
  @Setter
  private Long sourceAccountId;

  // 对手账户
  @Getter
  @Setter
  private Long matchAccountId;

  // 币种
  @Getter
  @Setter
  @Enumerated(EnumType.STRING)
  private CurrencyCode currency;

  // 发生金额
  @Getter
  @Setter
  private Double occurAmount;

  @Getter
  @Temporal(TemporalType.TIMESTAMP)
  private Date occurTime = new Timestamp(System.currentTimeMillis());

}

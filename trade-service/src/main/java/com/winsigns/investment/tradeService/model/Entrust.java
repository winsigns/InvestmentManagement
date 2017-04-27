package com.winsigns.investment.tradeService.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.winsigns.investment.framework.model.AbstractEntity;
import com.winsigns.investment.tradeService.constant.CurrencyCode;
import com.winsigns.investment.tradeService.constant.EntrustStatus;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Entrust extends AbstractEntity {

  // 指令ID
  @Setter
  @Getter
  private Long instructionId;

  // 标的ID
  @Setter
  @Getter
  private Long securityId;

  // 交易服务
  @Setter
  @Getter
  private String tradeService;

  // 交易方向
  @Setter
  @Getter
  private String tradeType;

  // 经济公司
  @Setter
  @Getter
  private Long brokerageFirmId;

  // 委托类型
  @Setter
  @Getter
  private String priceType;

  // 币种
  @Setter
  @Getter
  @Enumerated(EnumType.STRING)
  private CurrencyCode currency;

  // 委托价格
  @Setter
  @Getter
  private Double entrustPrice;

  // 委托数量
  @Setter
  @Getter
  private Long entrustQuantity;

  // 委托状态
  @Setter
  @Getter
  @Enumerated(EnumType.STRING)
  private EntrustStatus status = EntrustStatus.DRAFT;

  // 委托时间
  @Getter
  @Setter
  @Temporal(TemporalType.TIMESTAMP)
  private Date entrustTime = new Timestamp(System.currentTimeMillis());

  // 成交
  @Getter
  @Setter
  @OneToMany(mappedBy = "entrust", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonIgnore
  private List<Done> dones = new ArrayList<Done>();

  // 委托信息
  @OneToMany(mappedBy = "entrust", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonIgnore
  @Setter
  @Getter
  private List<EntrustMessage> messages = new ArrayList<EntrustMessage>();

  public void addEntrustMessage(EntrustMessage message) {
    this.messages.add(message);
  }
}

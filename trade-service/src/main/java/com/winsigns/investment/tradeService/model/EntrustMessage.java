package com.winsigns.investment.tradeService.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.winsigns.investment.framework.model.AbstractEntity;
import com.winsigns.investment.tradeService.constant.EntrustMessageCode;
import com.winsigns.investment.tradeService.constant.EntrustMessageType;

import lombok.Getter;
import lombok.Setter;

@Entity
public class EntrustMessage extends AbstractEntity {

  // 委托
  @ManyToOne
  @JsonIgnore
  @Getter
  @Setter
  private Entrust entrust;

  // 字段名
  @Getter
  @Setter
  private String fieldName;

  // 消息类型
  @Enumerated(EnumType.STRING)
  @Getter
  @Setter
  private EntrustMessageType messageType;

  // 消息代码
  @Enumerated(EnumType.STRING)
  @Getter
  @Setter
  private EntrustMessageCode messageCode;

  // 消息内容
  @Getter
  @Setter
  private String message;

  public EntrustMessage() {

  }

  public EntrustMessage(Entrust entrust, String fieldName, EntrustMessageType messageType,
      EntrustMessageCode messageCode) {
    this.fieldName = fieldName;
    this.entrust = entrust;
    this.messageType = messageType;
    this.messageCode = messageCode;
    this.message = messageCode.i18n();
  }

  public EntrustMessage(Entrust entrust, String fieldName, EntrustMessageType messageType,
      EntrustMessageCode messageCode, String message) {
    this.fieldName = fieldName;
    this.entrust = entrust;
    this.messageType = messageType;
    this.messageCode = messageCode;
    this.message = message;
  }
}

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

  @ManyToOne
  @JsonIgnore
  @Getter
  @Setter
  private Entrust entrust;

  @Getter
  @Setter
  private String fieldName;

  @Enumerated(EnumType.STRING)
  @Getter
  @Setter
  private EntrustMessageType messageType;

  @Enumerated(EnumType.STRING)
  @Getter
  @Setter
  private EntrustMessageCode messageCode;

  public EntrustMessage() {

  }

  public EntrustMessage(Entrust entrust, String fieldName, EntrustMessageType messageType,
      EntrustMessageCode messageCode) {
    this.fieldName = fieldName;
    this.entrust = entrust;
    this.messageType = messageType;
    this.messageCode = messageCode;
  }
}

package com.winsigns.investment.investService.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.winsigns.investment.framework.model.AbstractEntity;
import com.winsigns.investment.investService.constant.InstructionMessageCode;
import com.winsigns.investment.investService.constant.InstructionMessageType;

import lombok.Getter;
import lombok.Setter;

@Entity
public class InstructionMessage extends AbstractEntity {

  // 指令
  @ManyToOne
  @JsonIgnore
  @Getter
  @Setter
  private Instruction instruction;

  // 字段名
  @Getter
  @Setter
  private String fieldName;

  // 消息类型
  @Enumerated(EnumType.STRING)
  @Getter
  @Setter
  private InstructionMessageType messageType;

  // 消息代码
  @Enumerated(EnumType.STRING)
  @Getter
  @Setter
  private InstructionMessageCode messageCode;

  // 消息内容
  @Getter
  @Setter
  private String message;

  public InstructionMessage() {

  }

  public InstructionMessage(Instruction instruction, String fieldName,
      InstructionMessageType messageType, InstructionMessageCode messageCode) {
    this.fieldName = fieldName;
    this.instruction = instruction;
    this.messageType = messageType;
    this.messageCode = messageCode;
    this.message = messageCode.i18n();
  }

  public InstructionMessage(Instruction instruction, String fieldName,
      InstructionMessageType messageType, InstructionMessageCode messageCode, String message) {
    this.fieldName = fieldName;
    this.instruction = instruction;
    this.messageType = messageType;
    this.messageCode = messageCode;
    this.message = message;
  }
}

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

  @ManyToOne
  @JsonIgnore
  @Getter
  @Setter
  private Instruction instruction;

  @Getter
  @Setter
  private String fieldName;

  @Enumerated(EnumType.STRING)
  @Getter
  @Setter
  private InstructionMessageType messageType;

  @Enumerated(EnumType.STRING)
  @Getter
  @Setter
  private InstructionMessageCode messageCode;

  public InstructionMessage() {

  }

  public InstructionMessage(Instruction instruction, String fieldName,
      InstructionMessageType messageType, InstructionMessageCode messageCode) {
    this.fieldName = fieldName;
    this.instruction = instruction;
    this.messageType = messageType;
    this.messageCode = messageCode;
  }
}

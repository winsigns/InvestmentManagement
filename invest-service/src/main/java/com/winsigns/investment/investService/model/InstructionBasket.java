package com.winsigns.investment.investService.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

/**
 * 指令篮子
 * 
 * @author yimingjin
 *
 */
@Entity(name = "instruction_basket")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("instruction_basket")
public class InstructionBasket extends Instruction {

  @Getter
  @Setter
  private String basketName;

  @OneToMany(mappedBy = "instructionBasket", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonIgnore
  @Getter
  @Setter
  List<Instruction> instructions = new ArrayList<Instruction>();

  public void addInstruction(Instruction instruction) {
    instruction.setInstructionBasket(this);
    this.instructions.add(instruction);
  }
}

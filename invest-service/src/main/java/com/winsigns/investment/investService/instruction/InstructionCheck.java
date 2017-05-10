package com.winsigns.investment.investService.instruction;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 指令检查的通用基类
 * 
 * @author yimingjin
 *
 */
public abstract class InstructionCheck implements IInstructionCheck {

  @Autowired
  InstructionCheckManager instructionCheckManager;

  @PostConstruct
  private void register() {
    instructionCheckManager.register(this);
  }
}

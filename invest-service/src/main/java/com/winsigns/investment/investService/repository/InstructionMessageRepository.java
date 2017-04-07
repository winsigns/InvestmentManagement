package com.winsigns.investment.investService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.winsigns.investment.investService.model.Instruction;
import com.winsigns.investment.investService.model.InstructionMessage;

public interface InstructionMessageRepository extends JpaRepository<InstructionMessage, Long> {

  void deleteByInstruction(Instruction instruction);

}

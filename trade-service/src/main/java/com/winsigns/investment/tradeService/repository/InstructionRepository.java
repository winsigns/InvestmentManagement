package com.winsigns.investment.tradeService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.winsigns.investment.tradeService.model.Instruction;

@Transactional
public interface InstructionRepository extends JpaRepository<Instruction, Long> {

}

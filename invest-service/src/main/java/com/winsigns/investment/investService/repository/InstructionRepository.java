package com.winsigns.investment.investService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.winsigns.investment.investService.model.Instruction;

@Transactional
public interface InstructionRepository extends JpaRepository<Instruction, Long> {

}

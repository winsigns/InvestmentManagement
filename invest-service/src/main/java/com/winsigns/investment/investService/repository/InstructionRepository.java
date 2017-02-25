package com.winsigns.investment.investService.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.winsigns.investment.investService.model.Instruction;

@Transactional
public interface InstructionRepository extends JpaRepository<Instruction, Long> {

    public List<Instruction> findByCreateDate(Date createDate);

}

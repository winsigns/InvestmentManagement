package com.winsigns.investment.invest.model;

import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;

@Transactional
public interface InstructionRepository extends CrudRepository<Instruction, String> {
	public Instruction findByInstructId(String instruct_id);
}


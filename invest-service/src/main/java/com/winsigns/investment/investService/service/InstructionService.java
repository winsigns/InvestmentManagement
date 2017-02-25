package com.winsigns.investment.investService.service;

import java.util.Collection;
import java.util.Currency;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winsigns.investment.investService.command.CreateInstructionCommand;
import com.winsigns.investment.investService.command.UpdateInstructionCommand;
import com.winsigns.investment.investService.constant.InstructionStatus;
import com.winsigns.investment.investService.model.Instruction;
import com.winsigns.investment.investService.repository.InstructionRepository;

@Service
public class InstructionService {

    Logger log = LoggerFactory.getLogger(InstructionService.class);

    @Autowired
    InstructionRepository instructionRepository;

    @Transactional
    public Instruction addInstruction(CreateInstructionCommand instructionCommand) {

        Instruction newInstruction = new Instruction();

        newInstruction.setPortfolioId(instructionCommand.getPortfolioId());
        newInstruction.setSecurityId(instructionCommand.getSecurityId());
        newInstruction.setInvestSvc(instructionCommand.getInvestSvc());
        newInstruction.setInvestDirection(instructionCommand.getInvestDirection());
        newInstruction.setCurrency(Currency.getInstance(instructionCommand.getCurrency()));
        newInstruction.setCostPrice(instructionCommand.getCostPrice());
        newInstruction.setVolumeType(instructionCommand.getVolumeType());
        newInstruction.setQuantity(instructionCommand.getQuantity());
        newInstruction.setAmount(instructionCommand.getAmount());
        newInstruction.setInstructionStatus(InstructionStatus.Draft);
        newInstruction.setCreateDate(new Date());

        return instructionRepository.save(newInstruction);
    }

    @Transactional
    public Instruction updateInstruction(Long instructionId, UpdateInstructionCommand instructionCommand) {
        Instruction instruction = instructionRepository.findOne(instructionId);

        instruction.setPortfolioId(instructionCommand.getPortfolioId());
        instruction.setSecurityId(instructionCommand.getSecurityId());
        instruction.setInvestSvc(instructionCommand.getInvestSvc());
        instruction.setInvestDirection(instructionCommand.getInvestDirection());
        instruction.setCurrency(Currency.getInstance(instructionCommand.getCurrency()));
        instruction.setCostPrice(instructionCommand.getCostPrice());
        instruction.setVolumeType(instructionCommand.getVolumeType());
        instruction.setQuantity(instructionCommand.getQuantity());
        instruction.setAmount(instructionCommand.getAmount());

        return instructionRepository.save(instruction);
    }

    public void deleteInstruction(Long instructionId) {

        instructionRepository.delete(instructionId);
    }

    public Instruction findOne(Long instructionId) {
        return instructionRepository.findOne(instructionId);
    }

    public Collection<Instruction> findByCreateDate(Date createDate) {
        return instructionRepository.findByCreateDate(createDate);
    }

    public Collection<Instruction> findAll() {
        return instructionRepository.findAll();
    }

    public boolean commitInstruction(Long instructionId) {

        Instruction instruction = instructionRepository.findOne(instructionId);

        if (instruction.getInstructionStatus() != InstructionStatus.Draft)
            return false;

        instruction.setInstructionStatus(InstructionStatus.Executing);
        instructionRepository.save(instruction);

        // TODO 向kafka 发送异步请求

        return true;
    }

}

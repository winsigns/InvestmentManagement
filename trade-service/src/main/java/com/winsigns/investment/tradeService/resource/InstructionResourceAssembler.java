package com.winsigns.investment.tradeService.resource;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.winsigns.investment.tradeService.controller.InstructionController;
import com.winsigns.investment.tradeService.model.Instruction;

public class InstructionResourceAssembler extends ResourceAssemblerSupport<Instruction, InstructionResource> {

	public InstructionResourceAssembler() {
		super(InstructionController.class, InstructionResource.class);
	}

	@Override
	public InstructionResource toResource(Instruction instruction) {
		return createResourceWithId(instruction.getId(), instruction);
	}

	@Override
	protected InstructionResource instantiateResource(Instruction entity) {
		return new InstructionResource(entity);
	}
}

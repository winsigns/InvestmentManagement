package com.winsigns.investment.investService.resource;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.winsigns.investment.investService.controller.InstructionController;
import com.winsigns.investment.investService.model.Instruction;

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

package com.winsigns.investment.investService.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.winsigns.investment.investService.constant.InstructionStatus;
import com.winsigns.investment.investService.controller.InstructionController;
import com.winsigns.investment.investService.model.Instruction;

public class InstructionResourceAssembler
    extends ResourceAssemblerSupport<Instruction, InstructionResource> {

  public InstructionResourceAssembler() {
    super(InstructionController.class, InstructionResource.class);
  }

  @Override
  public InstructionResource toResource(Instruction instruction) {
    InstructionResource instructionResource =
        createResourceWithId(instruction.getId(), instruction);
    if (instruction.getInstructionStatus() == InstructionStatus.Draft) {
      instructionResource
          .add(linkTo(methodOn(InstructionController.class).commitInstruction(instruction.getId()))
              .withRel("commit"));
    }

    return instructionResource;
  }

  @Override
  protected InstructionResource instantiateResource(Instruction entity) {
    return new InstructionResource(entity);
  }
}

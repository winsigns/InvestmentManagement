package com.winsigns.investment.investService.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.util.Assert;

import com.winsigns.investment.investService.constant.InstructionStatus;
import com.winsigns.investment.investService.controller.InstructionBasketController;
import com.winsigns.investment.investService.controller.InstructionController;
import com.winsigns.investment.investService.model.Instruction;

public class InstructionResourceAssembler
    implements ResourceAssembler<Instruction, InstructionResource> {

  private final Class<?> instructionControllerClass = InstructionController.class;
  private final Class<?> basketControllerClass = InstructionBasketController.class;

  final static String fundURL = "http://fund-service/funds/tree?investManagerId=%d";
  final static String securityURL = "http://TODO";

  @Override
  public InstructionResource toResource(Instruction instruction) {
    InstructionResource resource = createResourceWithId(instruction.getId(), instruction);
    if (!instruction.isOfBasket() && instruction.getExecutionStatus() == InstructionStatus.DRAFT) {
      resource
          .add(linkTo(methodOn(InstructionController.class).commitInstruction(instruction.getId()))
              .withRel("commit"));
    }

    // 准备各项过滤条件的Link
    // 1.基金产品与组合
    // 2.投资标的
    resource.add(new Link(String.format(fundURL, instruction.getInvestManagerId()), "fundtree"));
    resource
        .add(new Link(String.format(securityURL, instruction.getInvestManagerId()), "securities"));
    return resource;
  }

  public List<InstructionResource> toResources(Iterable<? extends Instruction> entities) {

    Assert.notNull(entities);
    List<InstructionResource> result = new ArrayList<InstructionResource>();

    for (Instruction entity : entities) {
      result.add(toResource(entity));
    }

    return result;
  }

  protected InstructionResource createResourceWithId(Object id, Instruction entity) {
    return createResourceWithId(id, entity, new Object[0]);
  }

  protected InstructionResource createResourceWithId(Object id, Instruction entity,
      Object... parameters) {

    Assert.notNull(entity);
    Assert.notNull(id);

    InstructionResource instance = instantiateResource(entity);
    if (entity.isBasket()) {
      instance.add(linkTo(basketControllerClass, parameters).slash(id).withSelfRel());
    } else {
      instance.add(linkTo(instructionControllerClass, parameters).slash(id).withSelfRel());
    }
    return instance;
  }

  protected InstructionResource instantiateResource(Instruction entity) {
    return new InstructionResource(entity);
  }
}

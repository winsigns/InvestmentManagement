package com.winsigns.investment.generalService.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.winsigns.investment.generalService.controller.OperatorSequenceController;
import com.winsigns.investment.generalService.model.OperatorSequence;

public class OperatorSequenceResourceAssembler
    extends ResourceAssemblerSupport<OperatorSequence, OperatorSequenceResource> {

  public OperatorSequenceResourceAssembler() {
    super(OperatorSequenceController.class, OperatorSequenceResource.class);
  }

  @Override
  public OperatorSequenceResource toResource(OperatorSequence operatorSequence) {

    OperatorSequenceResource operatorSequenceResource = instantiateResource(operatorSequence);
    operatorSequenceResource.add(linkTo(OperatorSequenceController.class).withSelfRel());
    return operatorSequenceResource;
  }

  @Override
  protected OperatorSequenceResource instantiateResource(OperatorSequence entity) {
    return new OperatorSequenceResource(entity);
  }
}

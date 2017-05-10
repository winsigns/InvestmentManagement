package com.winsigns.investment.generalService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.winsigns.investment.generalService.model.OperatorSequence;
import com.winsigns.investment.generalService.resource.OperatorSequenceResource;
import com.winsigns.investment.generalService.resource.OperatorSequenceResourceAssembler;
import com.winsigns.investment.generalService.service.OperatorSequenceService;

@RestController
@RequestMapping("/operator-sequences")
public class OperatorSequenceController {

  @Autowired
  OperatorSequenceService operatorSequenceService;

  @GetMapping
  public OperatorSequenceResource getOperatorSequence() {

    OperatorSequence operatorSequence = operatorSequenceService.getOperatorSequence();

    return new OperatorSequenceResourceAssembler().toResource(operatorSequence);
  }
}

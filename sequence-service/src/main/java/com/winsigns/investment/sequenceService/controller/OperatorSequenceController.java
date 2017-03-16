package com.winsigns.investment.sequenceService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.core.Relation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.winsigns.investment.sequenceService.service.OperatorSequenceService;

@RestController
@RequestMapping("/operator-sequences")
@Relation(value = "operator-sequence", collectionRelation = "operator-sequences")
public class OperatorSequenceController {

  @Autowired
  OperatorSequenceService operatorSequenceService;

  @GetMapping
  public ResponseEntity<?> getOperatorSequence() {

    HttpHeaders responseHeaders = new HttpHeaders();

    String sequence = operatorSequenceService.getOperatorSequence();

    return new ResponseEntity<Object>(sequence, responseHeaders, HttpStatus.OK);
  }
}

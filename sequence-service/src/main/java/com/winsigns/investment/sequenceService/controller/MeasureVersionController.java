package com.winsigns.investment.sequenceService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.core.Relation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.winsigns.investment.sequenceService.service.MeasureVersionService;

@RestController
@RequestMapping("/measure-versions")
@Relation(value = "measure-version", collectionRelation = "measure-versions")
public class MeasureVersionController {

  @Autowired
  MeasureVersionService measureVersionService;

  @PostMapping
  public ResponseEntity<?> getMeasureVersion() {

    HttpHeaders responseHeaders = new HttpHeaders();

    String version = measureVersionService.getMeasureVersion();

    return new ResponseEntity<Object>(version, responseHeaders, HttpStatus.OK);
  }
}

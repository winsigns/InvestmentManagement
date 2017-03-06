package com.winsigns.investment.inventoryService.controller;

import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.winsigns.investment.inventoryService.command.CreatePositionCommand;
import com.winsigns.investment.inventoryService.model.Position;
import com.winsigns.investment.inventoryService.resource.PositionResource;
import com.winsigns.investment.inventoryService.resource.PositionResourceAssembler;
import com.winsigns.investment.inventoryService.service.PositionService;

@RestController
@RequestMapping(path = "/positions",
    produces = {HAL_JSON_VALUE, APPLICATION_JSON_VALUE, APPLICATION_JSON_UTF8_VALUE})
public class PositionController {

  @Autowired
  PositionService positionService;

  @GetMapping
  public Resources<PositionResource> readPositions() {
    Link link = linkTo(PositionController.class).withSelfRel();
    return new Resources<PositionResource>(
        new PositionResourceAssembler().toResources(positionService.findAll()), link);
  }

  @PostMapping
  public ResponseEntity<?> createPosition(
      @RequestBody CreatePositionCommand createPositionCommand) {

    Position position = positionService.addPosition(createPositionCommand);
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setLocation(
        linkTo(methodOn(PositionController.class).readPosition(position.getId())).toUri());
    return new ResponseEntity<Object>(position, responseHeaders, HttpStatus.CREATED);
  }

  @GetMapping("/{positionId}")
  public PositionResource readPosition(@PathVariable Long positionId) {
    Position position = positionService.findOne(positionId);
    PositionResource positionResource = new PositionResourceAssembler().toResource(position);

    return positionResource;
  }

}

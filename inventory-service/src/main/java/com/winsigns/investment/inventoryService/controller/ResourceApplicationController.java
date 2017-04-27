package com.winsigns.investment.inventoryService.controller;

import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.winsigns.investment.inventoryService.command.ApplyResourceCommand;
import com.winsigns.investment.inventoryService.service.ResourceApplicationService;

@RestController
@RequestMapping(path = "/inventories",
    produces = {HAL_JSON_VALUE, APPLICATION_JSON_VALUE, APPLICATION_JSON_UTF8_VALUE})
public class ResourceApplicationController {

  @Autowired
  ResourceApplicationService service;

  @PostMapping
  public ResponseEntity<?> applyInventory(
      @RequestBody ApplyResourceCommand applyInventoryCommand) {

    service.apply(applyInventoryCommand);

    HttpHeaders responseHeaders = new HttpHeaders();
    return new ResponseEntity<Object>(responseHeaders, HttpStatus.NO_CONTENT);
  }
}

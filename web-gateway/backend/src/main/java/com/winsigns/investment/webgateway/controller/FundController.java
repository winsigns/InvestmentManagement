package com.winsigns.investment.webgateway.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.winsigns.investment.webgateway.service.FundService;
import com.winsigns.investment.webgateway.command.FundCommand;

@RestController
@RequestMapping("/funds")
public class FundController {
  @Autowired
  private FundService fundService;

  // 获取所有基金
  @GetMapping
  public ResponseEntity<?> readFunds() {	  
    JsonNode funds = fundService.findAllFunds();  
    HttpHeaders responseHeaders = new HttpHeaders();
    return new ResponseEntity<Object>(funds, responseHeaders, HttpStatus.OK);
  }

  // 创设基金
  @PostMapping
  public ResponseEntity<?> createFund(@RequestBody FundCommand fundCommand) {
	System.out.println("调试：Post");
	System.out.println(fundCommand);
    return null;
  }
}

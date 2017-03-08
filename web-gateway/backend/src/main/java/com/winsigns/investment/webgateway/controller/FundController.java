package com.winsigns.investment.webgateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.winsigns.investment.webgateway.service.FundService;

@RestController
@RequestMapping("/funds")
public class FundController {
  @Autowired
  private FundService fundService;

  // 获取所有基金
  @GetMapping
  public ResponseEntity<?> readFunds() {
    // 外部调用获取指定的资金池
    JsonNode funds = fundService.findAllFunds();

    HttpHeaders responseHeaders = new HttpHeaders();

    return new ResponseEntity<Object>(funds, responseHeaders, HttpStatus.OK);
  }

}

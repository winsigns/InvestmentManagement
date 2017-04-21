package com.winsigns.investment.tradeService.controller;

import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.winsigns.investment.tradeService.resource.DoneResource;
import com.winsigns.investment.tradeService.resource.DoneResourceAssembler;
import com.winsigns.investment.tradeService.service.DoneService;

@RestController
@RequestMapping(path = "/dones",
    produces = {HAL_JSON_VALUE, APPLICATION_JSON_VALUE, APPLICATION_JSON_UTF8_VALUE})
public class DoneController {

  @Autowired
  DoneService doneService;

  /**
   * 获取一条具体的成交
   * 
   * @param doneId
   * @return
   */
  @GetMapping("/{doneId}")
  public DoneResource readDone(@PathVariable Long doneId) {
    return new DoneResourceAssembler().toResource(doneService.readDone(doneId));
  }
}

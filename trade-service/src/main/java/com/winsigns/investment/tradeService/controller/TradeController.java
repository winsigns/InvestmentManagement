package com.winsigns.investment.tradeService.controller;

import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.winsigns.investment.framework.hal.Resources;
import com.winsigns.investment.tradeService.command.CommitInstructionCommand;
import com.winsigns.investment.tradeService.resource.TradeServiceResource;
import com.winsigns.investment.tradeService.resource.TradeServiceResourceAssembler;
import com.winsigns.investment.tradeService.service.common.TradeServiceManager;

/**
 * 交易服务的通用入口
 * 
 * @author yimingjin
 *
 */
@RestController
@RequestMapping(path = "/trades",
    produces = {HAL_JSON_VALUE, APPLICATION_JSON_VALUE, APPLICATION_JSON_UTF8_VALUE})
public class TradeController {

  @Autowired
  TradeServiceManager tradeServiceManager;

  @GetMapping
  public Resources<TradeServiceResource> getAvailableTradeServices() {
    Link link = linkTo(TradeController.class).withSelfRel();
    return new Resources<TradeServiceResource>(new TradeServiceResourceAssembler()
        .toResources(tradeServiceManager.getAvailableTradeServices(null, null)), link);
  }

  /**
   * 接受一条投资服务的指令
   * 
   * @param CommitInstructionCmd
   * @return
   */
  @PostMapping
  public ResponseEntity<?> acceptInstrucion(
      @RequestBody CommitInstructionCommand CommitInstructionCmd) {
    tradeServiceManager.acceptInstruction(CommitInstructionCmd);
    return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
  }
}

package com.winsigns.investment.tradeService.controller;

import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.winsigns.investment.tradeService.command.CommitInstructionCommand;
import com.winsigns.investment.tradeService.resource.TradeServiceResource;
import com.winsigns.investment.tradeService.resource.TradeServiceResourceAssembler;
import com.winsigns.investment.tradeService.service.TradeServiceManager;

@RestController
@RequestMapping(path = "/trade",
    produces = {HAL_JSON_VALUE, APPLICATION_JSON_VALUE, APPLICATION_JSON_UTF8_VALUE})
public class TradeController {

  @Autowired
  TradeServiceManager tradeServiceManager;

  @GetMapping
  public Resources<TradeServiceResource> getAvailableTradeServices() {
    Link link = linkTo(TradeController.class).withSelfRel();
    return new Resources<TradeServiceResource>(new TradeServiceResourceAssembler()
        .toResources(tradeServiceManager.getAvailableTradeServices(null)), link);
  }

  @PostMapping
  public void acceptInstrucion(@RequestBody CommitInstructionCommand CommitInstructionCmd) {
    CommitInstructionCmd.setInvestSvc("stock");
    tradeServiceManager.acceptInstruction(CommitInstructionCmd);
  }
}

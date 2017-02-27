package com.winsigns.investment.fundService.controller;

import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.winsigns.investment.fundService.command.UpdateExternalTradeAccountCommand;
import com.winsigns.investment.fundService.resource.ExternalTradeAccountResource;
import com.winsigns.investment.fundService.resource.ExternalTradeAccountResourceAssembler;
import com.winsigns.investment.fundService.service.ExternalTradeAccountService;

@RestController
@RequestMapping(path = "/external-trade-accounts",
    produces = {HAL_JSON_VALUE, APPLICATION_JSON_VALUE, APPLICATION_JSON_UTF8_VALUE})
public class ExternalTradeAccountController {

  @Autowired
  ExternalTradeAccountService externalTradeAccountService;

  // 获取外部交易账户
  @GetMapping
  public Resources<ExternalTradeAccountResource> readExternalTradeAccounts() {
    Link link = linkTo(ExternalTradeAccountController.class).withSelfRel();
    return new Resources<ExternalTradeAccountResource>(new ExternalTradeAccountResourceAssembler()
        .toResources(externalTradeAccountService.findAll()), link);
  }

  // 获取指定的外部交易账户
  @GetMapping("/{externalTradeAccountId}")
  public ExternalTradeAccountResource readExternalTradeAccount(
      @PathVariable Long externalTradeAccountId) {
    return new ExternalTradeAccountResourceAssembler()
        .toResource(externalTradeAccountService.findOne(externalTradeAccountId));
  }

  // 修改指定的外部交易账户
  @PutMapping("/{externalTradeAccountId}")
  public ExternalTradeAccountResource updateExternalTradeAccount(
      @PathVariable Long externalTradeAccountId,
      @RequestBody UpdateExternalTradeAccountCommand updateExternalTradeAccountCommand) {
    return new ExternalTradeAccountResourceAssembler().toResource(externalTradeAccountService
        .updateExternalTradeAccount(externalTradeAccountId, updateExternalTradeAccountCommand));
  }

  // 删除指定的外部交易账户
  @DeleteMapping("/{externalTradeAccountId}")
  public ResponseEntity<?> deleteExternalTradeAccount(@PathVariable Long externalTradeAccountId) {
    externalTradeAccountService.deleteExternalTradeAccount(externalTradeAccountId);
    return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
  }
}

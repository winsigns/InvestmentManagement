package com.winsigns.investment.fundService.controller;

import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.core.Relation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.winsigns.investment.fundService.command.CreateExternalTradeAccountCommand;
import com.winsigns.investment.fundService.command.UpdateExternalCapitalAccountCommand;
import com.winsigns.investment.fundService.integration.CallInventoryService;
import com.winsigns.investment.fundService.model.ExternalCapitalAccount;
import com.winsigns.investment.fundService.model.ExternalTradeAccount;
import com.winsigns.investment.fundService.resource.ExternalCapitalAccountResource;
import com.winsigns.investment.fundService.resource.ExternalCapitalAccountResourceAssembler;
import com.winsigns.investment.fundService.resource.ExternalTradeAccountResource;
import com.winsigns.investment.fundService.resource.ExternalTradeAccountResourceAssembler;
import com.winsigns.investment.fundService.service.ExternalCapitalAccountService;
import com.winsigns.investment.fundService.service.ExternalTradeAccountService;

import net.minidev.json.JSONArray;

@RestController
@RequestMapping(path = "/external-capital-accounts",
    produces = {HAL_JSON_VALUE, APPLICATION_JSON_VALUE, APPLICATION_JSON_UTF8_VALUE})
public class ExternalCapitalAccountController {

  @Autowired
  ExternalCapitalAccountService externalCapitalAccountService;

  @Autowired
  ExternalTradeAccountService externalTradeAccountService;

  @Autowired
  CallInventoryService callInventoryService;

  // 获取外部资金账户
  @GetMapping
  public Resources<ExternalCapitalAccountResource> readExternalCapitalAccounts() {
    Link link = linkTo(ExternalCapitalAccountController.class).withSelfRel();
    return new Resources<ExternalCapitalAccountResource>(
        new ExternalCapitalAccountResourceAssembler()
            .toResources(externalCapitalAccountService.findAll()),
        link);
  }

  // 获取指定外部资金账户
  @GetMapping("/{externalCapitalAccountId}")
  public ExternalCapitalAccountResource readExternalCapitalAccount(
      @PathVariable Long externalCapitalAccountId) {
    ExternalCapitalAccount externalCapitalAccount =
        externalCapitalAccountService.findOne(externalCapitalAccountId);
    ExternalCapitalAccountResource externalCapitalAccountResource =
        new ExternalCapitalAccountResourceAssembler().toResource(externalCapitalAccount);

    List<ExternalTradeAccount> externalTradeAccounts =
        externalCapitalAccount.getExternalTradeAccounts();
    if (!externalTradeAccounts.isEmpty()) {
      externalCapitalAccountResource.add(
          ExternalTradeAccount.class.getAnnotation(Relation.class).collectionRelation(),
          new ExternalTradeAccountResourceAssembler().toResources(externalTradeAccounts));
    }

    // 外部调用获取指定的资金池
    JSONArray ecaCashPools = callInventoryService.getECACashPools(externalCapitalAccountId);
    externalCapitalAccountResource.add("eca-cash-pools", ecaCashPools);

    return externalCapitalAccountResource;
  }

  // 修改指定外部资金账户
  @PutMapping("/{externalCapitalAccountId}")
  public ExternalCapitalAccountResource updateExternalCapitalAccount(
      @PathVariable Long externalCapitalAccountId,
      @RequestBody UpdateExternalCapitalAccountCommand externalCapitalAccountCommand) {
    return new ExternalCapitalAccountResourceAssembler().toResource(externalCapitalAccountService
        .updateExternalCapitalAccount(externalCapitalAccountId, externalCapitalAccountCommand));
  }

  // 删除指定外部资金账户
  @DeleteMapping("/{externalCapitalAccountId}")
  public ResponseEntity<?> deleteExternalCapitalAccount(
      @PathVariable Long externalCapitalAccountId) {
    externalCapitalAccountService.deleteExternalCapitalAccount(externalCapitalAccountId);
    return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
  }

  // 获取指定外部资金账户下的外部交易账户
  @GetMapping("/{externalCapitalAccountId}/external-trade-accounts")
  public Resources<ExternalTradeAccountResource> readExternalTradeAccounts(
      @PathVariable Long externalCapitalAccountId) {

    Link link = linkTo(methodOn(ExternalCapitalAccountController.class)
        .readExternalTradeAccounts(externalCapitalAccountId)).withSelfRel();
    Link linkECA = linkTo(methodOn(ExternalCapitalAccountController.class)
        .readExternalCapitalAccount(externalCapitalAccountId))
            .withRel(ExternalCapitalAccount.class.getAnnotation(Relation.class).value());

    return new Resources<ExternalTradeAccountResource>(
        new ExternalTradeAccountResourceAssembler().toResources(
            externalTradeAccountService.findByExternalCapitalAccountId(externalCapitalAccountId)),
        link, linkECA);
  }

  // 在指定外部资金账户下创建外部交易账户
  @PostMapping("/{externalCapitalAccountId}/external-trade-accounts")
  public ResponseEntity<?> createExternalTradeAccount(@PathVariable Long externalCapitalAccountId,
      @RequestBody CreateExternalTradeAccountCommand createExternalTradeAccountCommand) {

    createExternalTradeAccountCommand.setExternalCapitalAccountId(externalCapitalAccountId);
    ExternalTradeAccount externalTradeAccount =
        externalTradeAccountService.addExternalTradeAccount(createExternalTradeAccountCommand);
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setLocation(linkTo(methodOn(ExternalTradeAccountController.class)
        .readExternalTradeAccount(externalTradeAccount.getId())).toUri());
    return new ResponseEntity<Object>(externalTradeAccount, responseHeaders, HttpStatus.CREATED);
  }

  // 在指定外部资金账户下删除外部交易账户
  @DeleteMapping("/{externalCapitalAccountId}/external-trade-accounts")
  public ResponseEntity<?> deleteExternalTradeAccounts() {
    // TODO
    return null;
  }

}

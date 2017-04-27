package com.winsigns.investment.fundService.controller;

import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.winsigns.investment.framework.hal.Resources;
import com.winsigns.investment.fundService.command.CreateExternalCapitalAccountCommand;
import com.winsigns.investment.fundService.command.CreateFundAccountCommand;
import com.winsigns.investment.fundService.command.CreateFundCommand;
import com.winsigns.investment.fundService.command.UpdateFundCommand;
import com.winsigns.investment.fundService.model.ExternalCapitalAccount;
import com.winsigns.investment.fundService.model.Fund;
import com.winsigns.investment.fundService.model.FundAccount;
import com.winsigns.investment.fundService.model.InvestManager;
import com.winsigns.investment.fundService.resource.ExternalCapitalAccountResource;
import com.winsigns.investment.fundService.resource.ExternalCapitalAccountResourceAssembler;
import com.winsigns.investment.fundService.resource.FundAccountResource;
import com.winsigns.investment.fundService.resource.FundAccountResourceAssembler;
import com.winsigns.investment.fundService.resource.FundResource;
import com.winsigns.investment.fundService.resource.FundResourceAssembler;
import com.winsigns.investment.fundService.resource.FundTreeResource;
import com.winsigns.investment.fundService.resource.FundTreeResourceAssembler;
import com.winsigns.investment.fundService.service.ExternalCapitalAccountService;
import com.winsigns.investment.fundService.service.FundAccountService;
import com.winsigns.investment.fundService.service.FundService;
import com.winsigns.investment.fundService.service.InvestManagerService;

@RestController
@RequestMapping(path = "/funds",
    produces = {HAL_JSON_VALUE, APPLICATION_JSON_VALUE, APPLICATION_JSON_UTF8_VALUE})

public class FundController {

  @Autowired
  private FundService fundService;

  @Autowired
  private FundAccountService fundAccountService;

  @Autowired
  private ExternalCapitalAccountService externalCapitalAccountService;

  @Autowired
  InvestManagerService investManagerService;

  // 获取所有基金
  @GetMapping
  public Resources<FundResource> readFunds() {
    Link link = linkTo(FundController.class).withSelfRel();
    Link treeLink = linkTo(methodOn(FundController.class).readFundTree(null)).withRel("tree");
    return new Resources<FundResource>(
        new FundResourceAssembler().toResources(fundService.findFunds()), link, treeLink);
  }

  /**
   * 获取基金、产品账户、投资组合的关系树
   * 
   * @param investManagerId 根据投资经理过滤
   * @return
   */
  @GetMapping(path = "/tree")
  public Resources<FundTreeResource> readFundTree(
      @RequestParam(required = false) Long investManagerId) {
    Link link = linkTo(methodOn(FundController.class).readFundTree(null)).withSelfRel();
    if (investManagerId != null) {
      InvestManager investManager = investManagerService.findOne(investManagerId);
      if (investManager == null) {
        // TODO 出错信息
        return null;

      } else {
        List<Fund> funds = fundService.findFunds(investManager);
        return new Resources<FundTreeResource>(
            new FundTreeResourceAssembler(investManager).toResources(funds), link);
      }

    } else {
      List<Fund> funds = fundService.findFunds();
      return new Resources<FundTreeResource>(new FundTreeResourceAssembler(null).toResources(funds),
          link);
    }

  }

  // 创建一个新的基金
  @PostMapping
  public ResponseEntity<?> createFund(@RequestBody CreateFundCommand createFundCommand) {

    HttpHeaders responseHeaders = new HttpHeaders();
    Fund fund = fundService.addFund(createFundCommand);
    responseHeaders
        .setLocation(linkTo(methodOn(FundController.class).readFund(fund.getId())).toUri());

    return new ResponseEntity<Object>(fund, responseHeaders, HttpStatus.CREATED);
  }

  // 删除所有基金
  @DeleteMapping
  public ResponseEntity<?> deleteFunds() {
    // TODO 删除所有基金
    return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
  }

  // 获取指定的基金
  @GetMapping("/{fundId}")
  public FundResource readFund(@PathVariable Long fundId) {
    Fund fund = fundService.findOne(fundId);
    FundResource fundResource = new FundResourceAssembler().toResource(fund);

    // 增加内嵌的基金产品账户
    List<FundAccount> fundAccounts = fund.getFundAccounts();
    if (!fundAccounts.isEmpty()) {
      fundResource.add(FundAccountResource.class.getAnnotation(Relation.class).collectionRelation(),
          new FundAccountResourceAssembler().toResources(fundAccounts));
    }

    // 增加内嵌的外部资金账户
    List<ExternalCapitalAccount> externalCapitalAccounts = fund.getExternalCapitalAccounts();
    if (!externalCapitalAccounts.isEmpty()) {
      fundResource.add(
          ExternalCapitalAccountResource.class.getAnnotation(Relation.class).collectionRelation(),
          new ExternalCapitalAccountResourceAssembler().toResources(externalCapitalAccounts));
    }

    return fundResource;
  }

  // 修改指定基金
  @PutMapping("/{fundId}")
  public FundResource updateFund(@PathVariable Long fundId,
      @RequestBody UpdateFundCommand updateFundCommand) {
    return new FundResourceAssembler()
        .toResource(fundService.updateFund(fundId, updateFundCommand));
  }

  // 删除指定基金
  @DeleteMapping("/{fundId}")
  public ResponseEntity<?> deleteFund(@PathVariable("fundId") Long id) {
    fundService.deleteFund(id);
    return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
  }

  // 获取指定基金的产品账户
  @GetMapping("/{fundId}/fund-accounts")
  public Resources<FundAccountResource> readFundAccounts(@PathVariable Long fundId) {
    Link link = linkTo(methodOn(FundController.class).readFundAccounts(fundId)).withSelfRel();
    Link linkFund = linkTo(methodOn(FundController.class).readFund(fundId))
        .withRel(Fund.class.getAnnotation(Relation.class).value());

    return new Resources<FundAccountResource>(
        new FundAccountResourceAssembler().toResources(fundAccountService.findByFundId(fundId)),
        link, linkFund);
  }

  // 在指定基金下面创建产品账户
  @PostMapping("/{fundId}/fund-accounts")
  public ResponseEntity<?> createFundAccount(@PathVariable Long fundId,
      @RequestBody CreateFundAccountCommand createFundAccountCommand) {

    createFundAccountCommand.setFundId(fundId);
    FundAccount fundAccount = fundAccountService.addFundAccount(createFundAccountCommand);
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setLocation(
        linkTo(methodOn(FundAccountController.class).readFundAccount(fundAccount.getId())).toUri());
    return new ResponseEntity<Object>(fundAccount, responseHeaders, HttpStatus.CREATED);
  }

  // 删除指定基金下面的所有产品账户
  @DeleteMapping("/{fundId}/fund-accounts")
  public ResponseEntity<?> deleteFundAccounts() {

    // TODO
    return null;
  }

  // 获取指定基金的外部资金账户
  @GetMapping("/{fundId}/external-capital-accounts")
  public Resources<ExternalCapitalAccountResource> readExternalCapitalAccounts(
      @PathVariable Long fundId) {
    Link link =
        linkTo(methodOn(FundController.class).readExternalCapitalAccounts(fundId)).withSelfRel();
    Link linkFund = linkTo(methodOn(FundController.class).readFund(fundId))
        .withRel(Fund.class.getAnnotation(Relation.class).value());

    return new Resources<ExternalCapitalAccountResource>(
        new ExternalCapitalAccountResourceAssembler()
            .toResources(externalCapitalAccountService.findByFundId(fundId)),
        link, linkFund);
  }

  // 在指定基金下创建外部资金账户
  @PostMapping("/{fundId}/external-capital-accounts")
  public ResponseEntity<?> createExternalCapitalAccount(@PathVariable Long fundId,
      @RequestBody CreateExternalCapitalAccountCommand createExternalCapitalAccountCommand) {

    createExternalCapitalAccountCommand.setFundId(fundId);
    ExternalCapitalAccount externalCapitalAccount = externalCapitalAccountService
        .addExternalCapitalAccount(createExternalCapitalAccountCommand);
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setLocation(linkTo(methodOn(ExternalCapitalAccountController.class)
        .readExternalCapitalAccount(externalCapitalAccount.getId())).toUri());
    return new ResponseEntity<Object>(externalCapitalAccount, responseHeaders, HttpStatus.CREATED);
  }

  // 删除指定基金下面的所有外部资金账户
  @DeleteMapping("/{fundId}/external-capital-accounts")
  public ResponseEntity<?> deleteExternalCapitalAccounts() {

    // TODO
    return null;
  }
}

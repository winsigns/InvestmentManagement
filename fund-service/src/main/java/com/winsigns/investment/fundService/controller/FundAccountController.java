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
import org.springframework.web.bind.annotation.RestController;

import com.winsigns.investment.framework.hal.Resources;
import com.winsigns.investment.fundService.command.CreatePortfolioCommand;
import com.winsigns.investment.fundService.command.UpdateFundAccountCommand;
import com.winsigns.investment.fundService.model.FundAccount;
import com.winsigns.investment.fundService.model.Portfolio;
import com.winsigns.investment.fundService.resource.FundAccountResource;
import com.winsigns.investment.fundService.resource.FundAccountResourceAssembler;
import com.winsigns.investment.fundService.resource.PortfolioResource;
import com.winsigns.investment.fundService.resource.PortfolioResourceAssembler;
import com.winsigns.investment.fundService.service.FundAccountService;
import com.winsigns.investment.fundService.service.PortfolioService;

@RestController
@RequestMapping(path = "/fund-accounts",
    produces = {HAL_JSON_VALUE, APPLICATION_JSON_VALUE, APPLICATION_JSON_UTF8_VALUE})
public class FundAccountController {

  @Autowired
  FundAccountService fundAccountService;

  @Autowired
  private PortfolioService portfolioService;

  // 获取所有基金产品账户
  @GetMapping
  public Resources<FundAccountResource> readFundAccounts() {
    Link link = linkTo(FundAccountController.class).withSelfRel();
    return new Resources<FundAccountResource>(
        new FundAccountResourceAssembler().toResources(fundAccountService.findAll()), link);
  }

  // 获取指定的产品账户
  @GetMapping("/{fundAccountId}")
  public FundAccountResource readFundAccount(@PathVariable Long fundAccountId) {

    FundAccount fundAccount = fundAccountService.findOne(fundAccountId);
    FundAccountResource fundAccountResource =
        new FundAccountResourceAssembler().toResource(fundAccount);

    // 增加内嵌的投资组合
    List<Portfolio> portfolios = fundAccount.getPortfolios();
    if (!portfolios.isEmpty()) {
      fundAccountResource.add(
          PortfolioResource.class.getAnnotation(Relation.class).collectionRelation(),
          new PortfolioResourceAssembler().toResources(portfolios));
    }
    return fundAccountResource;
  }

  // 修改指定的产品账户
  @PutMapping("/{fundAccountId}")
  public FundAccountResource updateFundAccount(@PathVariable Long fundAccountId,
      @RequestBody UpdateFundAccountCommand updateFundAccountCommand) {
    return new FundAccountResourceAssembler()
        .toResource(fundAccountService.updateFundAccount(fundAccountId, updateFundAccountCommand));
  }

  // 删除指定的产品账户
  @DeleteMapping("/{fundAccountId}")
  public ResponseEntity<?> deleteFundAccount(@PathVariable Long fundAccountId) {
    fundAccountService.deleteFundAccount(fundAccountId);
    return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
  }

  // 获取指定产品账户下的所有投资组合
  @GetMapping("/{fundAccountId}/portfolios")
  public Resources<PortfolioResource> readPortfolios(@PathVariable Long fundAccountId) {
    Link link =
        linkTo(methodOn(FundAccountController.class).readPortfolios(fundAccountId)).withSelfRel();
    Link linkFundAccount =
        linkTo(methodOn(FundAccountController.class).readFundAccount(fundAccountId))
            .withRel(FundAccountResource.class.getAnnotation(Relation.class).value());

    return new Resources<PortfolioResource>(new PortfolioResourceAssembler()
        .toResources(portfolioService.findByFundAccountId(fundAccountId)), link, linkFundAccount);
  }

  // 在指定产品账户下创建投资组合
  @PostMapping("/{fundAccountId}/portfolios")
  public ResponseEntity<?> createPortfolio(@PathVariable Long fundAccountId,
      @RequestBody CreatePortfolioCommand createPortfolioCommand) {

    createPortfolioCommand.setFundAccountId(fundAccountId);
    Portfolio portfolio = portfolioService.addPortfolio(createPortfolioCommand);
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setLocation(
        linkTo(methodOn(PortfolioController.class).readPortfolio(portfolio.getId())).toUri());
    return new ResponseEntity<Object>(portfolio, responseHeaders, HttpStatus.CREATED);
  }

  // 删除指定产品账户的所有投资组合
  @DeleteMapping("/{fundAccountId}/portfolios")
  public ResponseEntity<?> deletePortfolios() {
    // TODO
    return null;
  }

}

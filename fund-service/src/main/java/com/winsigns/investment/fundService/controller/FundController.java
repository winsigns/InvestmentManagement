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

import com.winsigns.investment.fundService.command.CreateFundCommand;
import com.winsigns.investment.fundService.command.UpdateFundCommand;
import com.winsigns.investment.fundService.model.ExternalCapitalAccount;
import com.winsigns.investment.fundService.model.Fund;
import com.winsigns.investment.fundService.model.FundAccount;
import com.winsigns.investment.fundService.resource.ExternalCapitalAccountResource;
import com.winsigns.investment.fundService.resource.ExternalCapitalAccountResourceAssembler;
import com.winsigns.investment.fundService.resource.ExternalTradeAccountResource;
import com.winsigns.investment.fundService.resource.ExternalTradeAccountResourceAssembler;
import com.winsigns.investment.fundService.resource.FundAccountResource;
import com.winsigns.investment.fundService.resource.FundAccountResourceAssembler;
import com.winsigns.investment.fundService.resource.FundResource;
import com.winsigns.investment.fundService.resource.FundResourceAssembler;
import com.winsigns.investment.fundService.resource.PortfolioResource;
import com.winsigns.investment.fundService.resource.PortfolioResourceAssembler;
import com.winsigns.investment.fundService.service.ExternalCapitalAccountService;
import com.winsigns.investment.fundService.service.ExternalTradeAccountService;
import com.winsigns.investment.fundService.service.FundAccountService;
import com.winsigns.investment.fundService.service.FundService;
import com.winsigns.investment.fundService.service.PortfolioService;

@RestController
@RequestMapping(path = "/funds", produces = { HAL_JSON_VALUE, APPLICATION_JSON_VALUE, APPLICATION_JSON_UTF8_VALUE })

public class FundController {

    @Autowired
    private FundService fundService;

    @Autowired
    private FundAccountService fundAccountService;

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private ExternalCapitalAccountService externalCapitalAccountService;

    @Autowired
    private ExternalTradeAccountService externalTradeAccountService;

    @GetMapping
    public Resources<FundResource> readFunds() {
        Link link = linkTo(FundController.class).withSelfRel();
        return new Resources<FundResource>(new FundResourceAssembler().toResources(fundService.findAllFunds()), link);
    }

    @GetMapping("/{fundId}")
    public FundResource readFund(@PathVariable Long fundId) {
        Fund fund = fundService.findOne(fundId);
        FundResource fundResource = new FundResourceAssembler().toResource(fund);

        List<FundAccount> fundAccounts = fund.getFundAccounts();
        if (fundAccounts.size() != 0)
            fundResource.add(FundAccount.class.getAnnotation(Relation.class).collectionRelation(),
                    new FundAccountResourceAssembler().toResources(fundAccounts));

        List<ExternalCapitalAccount> externalCapitalAccounts = fund.getExternalCapitalAccounts();
        if (externalCapitalAccounts.size() != 0)
            fundResource.add(ExternalCapitalAccount.class.getAnnotation(Relation.class).collectionRelation(),
                    new ExternalCapitalAccountResourceAssembler().toResources(externalCapitalAccounts));

        return fundResource;
    }

    @GetMapping("/{fundId}/fund-accounts")
    public Resources<FundAccountResource> readFundAccounts(@PathVariable Long fundId) {
        Link link = linkTo(FundAccountController.class).withSelfRel();
        return new Resources<FundAccountResource>(
                new FundAccountResourceAssembler().toResources(fundAccountService.findByFundId(fundId)), link);
    }

    @GetMapping("/{fundId}/fund-accounts/{fundAccountId}/portfolios")
    public Resources<PortfolioResource> readPortfolios(@PathVariable Long fundId, @PathVariable Long fundAccountId) {
        Link link = linkTo(PortfolioController.class).withSelfRel();
        return new Resources<PortfolioResource>(
                new PortfolioResourceAssembler().toResources(portfolioService.findByFundAccountId(fundAccountId)),
                link);
    }

    @GetMapping("/{fundId}/external-capital-accounts")
    public Resources<ExternalCapitalAccountResource> readExternalCapitalAccounts(@PathVariable Long fundId) {
        Link link = linkTo(ExternalCapitalAccount.class).withSelfRel();

        return new Resources<ExternalCapitalAccountResource>(new ExternalCapitalAccountResourceAssembler()
                .toResources(externalCapitalAccountService.findByFundId(fundId)), link);
    }

    @GetMapping("/{fundId}/external-capital-accounts/{externalCapitalAccountId}/external-trade-accounts")
    public Resources<ExternalTradeAccountResource> readExternalTradeAccounts(@PathVariable Long fundId,
            @PathVariable Long externalCapitalAccountId) {
        Link link = linkTo(ExternalTradeAccountController.class).withSelfRel();
        return new Resources<ExternalTradeAccountResource>(new ExternalTradeAccountResourceAssembler().toResources(
                externalTradeAccountService.findByExternalCapitalAccountId(externalCapitalAccountId)), link);
    }

    @PostMapping
    public ResponseEntity<?> createFund(@RequestBody CreateFundCommand createFundCommand) {

        HttpHeaders responseHeaders = new HttpHeaders();
        Fund fund = fundService.addFund(createFundCommand);
        responseHeaders.setLocation(linkTo(methodOn(FundController.class).readFund(fund.getId())).toUri());

        return new ResponseEntity<Object>(fund, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{fundId}")
    public FundResource updateFund(@PathVariable Long fundId, @RequestBody UpdateFundCommand updateFundCommand) {
        return new FundResourceAssembler().toResource(fundService.updateFund(fundId, updateFundCommand));
    }

    @DeleteMapping("/{fundId}")
    public ResponseEntity<?> deleteFund(@PathVariable("fundId") Long id) {
        fundService.deleteFund(id);
        return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    }

}

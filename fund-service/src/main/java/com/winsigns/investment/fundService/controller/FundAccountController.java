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

import com.winsigns.investment.fundService.command.CreateFundAccountCommand;
import com.winsigns.investment.fundService.command.UpdateFundAccountCommand;
import com.winsigns.investment.fundService.model.FundAccount;
import com.winsigns.investment.fundService.model.Portfolio;
import com.winsigns.investment.fundService.resource.FundAccountResource;
import com.winsigns.investment.fundService.resource.FundAccountResourceAssembler;
import com.winsigns.investment.fundService.resource.PortfolioResourceAssembler;
import com.winsigns.investment.fundService.service.FundAccountService;

@RestController
@RequestMapping(path = "/fund-accounts", produces = { HAL_JSON_VALUE, APPLICATION_JSON_VALUE,
        APPLICATION_JSON_UTF8_VALUE })
public class FundAccountController {

    @Autowired
    FundAccountService fundAccountService;

    @GetMapping
    public Resources<FundAccountResource> readFundAccounts() {
        Link link = linkTo(FundAccountController.class).withSelfRel();
        return new Resources<FundAccountResource>(
                new FundAccountResourceAssembler().toResources(fundAccountService.findAll()), link);
    }

    @GetMapping("/{fundAccountId}")
    public FundAccountResource readFundAccount(@PathVariable Long fundAccountId) {

        FundAccount fundAccount = fundAccountService.findOne(fundAccountId);
        FundAccountResource fundAccountResource = new FundAccountResourceAssembler()
                .toResource(fundAccountService.findOne(fundAccountId));

        List<Portfolio> portfolios = fundAccount.getPortfolios();
        if (portfolios.size() != 0)
            fundAccountResource.add(Portfolio.class.getAnnotation(Relation.class).collectionRelation(),
                    new PortfolioResourceAssembler().toResources(portfolios));
        return fundAccountResource;
    }

    @PostMapping
    public ResponseEntity<?> createFundAccount(@RequestBody CreateFundAccountCommand createFundAccountCommand) {

        FundAccount fundAccount = fundAccountService.addFundAccount(createFundAccountCommand);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(
                linkTo(methodOn(FundAccountController.class).readFundAccount(fundAccount.getId())).toUri());
        return new ResponseEntity<Object>(fundAccount, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{fundAccountId}")
    public FundAccountResource updateFundAccount(@PathVariable Long fundAccountId,
            @RequestBody UpdateFundAccountCommand updateFundAccountCommand) {
        return new FundAccountResourceAssembler()
                .toResource(fundAccountService.updateFundAccount(fundAccountId, updateFundAccountCommand));
    }

    @DeleteMapping("/{fundAccountId}")
    public ResponseEntity<?> deleteFundAccount(@PathVariable Long fundAccountId) {
        fundAccountService.deleteFundAccount(fundAccountId);
        return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    }

}

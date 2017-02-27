package com.winsigns.investment.fundService.controller;

import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.winsigns.investment.fundService.hal.HALResponse;
import com.winsigns.investment.fundService.model.ExternalCapitalAccount;
import com.winsigns.investment.fundService.model.ExternalTradeAccount;
import com.winsigns.investment.fundService.model.Fund;
import com.winsigns.investment.fundService.model.FundAccount;
import com.winsigns.investment.fundService.model.Portfolio;
import org.springframework.hateoas.core.Relation;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by colin on 2017/2/22.
 */

@RestController
public class RootController {

  @GetMapping(path = "/", produces = {HAL_JSON_VALUE, APPLICATION_JSON_VALUE,
      APPLICATION_JSON_UTF8_VALUE})
  public HttpEntity<HALResponse<String>> root() {
    HALResponse<String> halResponse = new HALResponse<String>("");

    halResponse.add(linkTo(methodOn((FundController.class)).readFunds())
        .withRel(Fund.class.getAnnotation(Relation.class).collectionRelation()));

    halResponse.add(linkTo(methodOn((FundAccountController.class)).readFundAccounts())
        .withRel(FundAccount.class.getAnnotation(Relation.class).collectionRelation()));

    halResponse.add(linkTo(methodOn((PortfolioController.class)).readPortfolios())
        .withRel(Portfolio.class.getAnnotation(Relation.class).collectionRelation()));

    halResponse.add(
        linkTo(methodOn((ExternalCapitalAccountController.class)).readExternalCapitalAccounts())
            .withRel(
                ExternalCapitalAccount.class.getAnnotation(Relation.class).collectionRelation()));

    halResponse
        .add(linkTo(methodOn((ExternalTradeAccountController.class)).readExternalTradeAccounts())
            .withRel(
                ExternalTradeAccount.class.getAnnotation(Relation.class).collectionRelation()));

    return new ResponseEntity<>(halResponse, HttpStatus.OK);
  }
}

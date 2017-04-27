package com.winsigns.investment.fundService.controller;

import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.hateoas.core.Relation;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.winsigns.investment.framework.hal.HALResponse;
import com.winsigns.investment.fundService.resource.ExternalCapitalAccountResource;
import com.winsigns.investment.fundService.resource.ExternalTradeAccountResource;
import com.winsigns.investment.fundService.resource.FundAccountResource;
import com.winsigns.investment.fundService.resource.FundResource;
import com.winsigns.investment.fundService.resource.PortfolioResource;

/**
 * Created by colin on 2017/2/22.
 */

@RestController
public class RootController {

  @GetMapping(path = "/",
      produces = {HAL_JSON_VALUE, APPLICATION_JSON_VALUE, APPLICATION_JSON_UTF8_VALUE})
  public HttpEntity<HALResponse<String>> root() {
    HALResponse<String> halResponse = new HALResponse<String>("");

    halResponse.add(linkTo(methodOn((FundController.class)).readFunds())
        .withRel(FundResource.class.getAnnotation(Relation.class).collectionRelation()));

    halResponse.add(linkTo(methodOn((FundAccountController.class)).readFundAccounts())
        .withRel(FundAccountResource.class.getAnnotation(Relation.class).collectionRelation()));

    halResponse.add(linkTo(methodOn((PortfolioController.class)).readPortfolios())
        .withRel(PortfolioResource.class.getAnnotation(Relation.class).collectionRelation()));

    halResponse.add(
        linkTo(methodOn((ExternalCapitalAccountController.class)).readExternalCapitalAccounts())
            .withRel(ExternalCapitalAccountResource.class.getAnnotation(Relation.class)
                .collectionRelation()));

    halResponse
        .add(linkTo(methodOn((ExternalTradeAccountController.class)).readExternalTradeAccounts())
            .withRel(ExternalTradeAccountResource.class.getAnnotation(Relation.class)
                .collectionRelation()));

    return new ResponseEntity<>(halResponse, HttpStatus.OK);
  }
}

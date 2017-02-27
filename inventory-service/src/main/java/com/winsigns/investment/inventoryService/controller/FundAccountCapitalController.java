package com.winsigns.investment.inventoryService.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.winsigns.investment.inventoryService.command.CreateFundAccountCapitalCommand;
import com.winsigns.investment.inventoryService.command.SetInvestmentLimitCommand;
import com.winsigns.investment.inventoryService.model.FundAccountCapital;
import com.winsigns.investment.inventoryService.model.FundAccountCapitalDetail;
import com.winsigns.investment.inventoryService.resource.FundAccountCapitalDetailResource;
import com.winsigns.investment.inventoryService.resource.FundAccountCapitalDetailResourceAssembler;
import com.winsigns.investment.inventoryService.resource.FundAccountCapitalResource;
import com.winsigns.investment.inventoryService.resource.FundAccountCapitalResourceAssembler;
import com.winsigns.investment.inventoryService.service.FundAccountCapitalDetailService;
import com.winsigns.investment.inventoryService.service.FundAccountCapitalService;

@RestController
@RequestMapping(path = "/fa-capitals",
    produces = {HAL_JSON_VALUE, APPLICATION_JSON_VALUE, APPLICATION_JSON_UTF8_VALUE})
public class FundAccountCapitalController {

  @Autowired
  FundAccountCapitalService fundAccountCapitalService;

  @Autowired
  FundAccountCapitalDetailService fundAccountCapitalDetailService;

  @GetMapping
  public Resources<FundAccountCapitalResource> readFundAccountCapitals() {
    Link link = linkTo(FundAccountCapitalController.class).withSelfRel();
    return new Resources<FundAccountCapitalResource>(
        new FundAccountCapitalResourceAssembler().toResources(fundAccountCapitalService.findAll()),
        link);
  }

  @GetMapping("/{faCapitalId}")
  public FundAccountCapitalResource readFundAccountCapital(@PathVariable Long faCapitalId) {
    FundAccountCapital fundAccountCapital = fundAccountCapitalService.findOne(faCapitalId);
    FundAccountCapitalResource fundAccountCapitalResource =
        new FundAccountCapitalResourceAssembler().toResource(fundAccountCapital);

    List<FundAccountCapitalDetail> fundAccountCapitalDetails =
        fundAccountCapital.getFundAccountCapitalDetails();
    if (fundAccountCapitalDetails.size() != 0)
      fundAccountCapitalResource.add(
          FundAccountCapitalDetail.class.getAnnotation(Relation.class).collectionRelation(),
          new FundAccountCapitalDetailResourceAssembler().toResources(fundAccountCapitalDetails));

    return fundAccountCapitalResource;
  }

  @GetMapping("/{faCapitalId}/fa-capital-details")
  public Resources<FundAccountCapitalDetailResource> readFundAccountCapitalDetails(
      @PathVariable Long faCapitalId) {
    Link link = linkTo(
        methodOn(FundAccountCapitalController.class).readFundAccountCapitalDetails(faCapitalId))
            .withSelfRel();
    return new Resources<FundAccountCapitalDetailResource>(
        new FundAccountCapitalDetailResourceAssembler()
            .toResources(fundAccountCapitalDetailService.findByFACapitalId(faCapitalId)),
        link);
  }

  @PostMapping
  public ResponseEntity<?> createFundAccountCapital(
      @RequestBody CreateFundAccountCapitalCommand createFundAccountCapitalCommand) {

    FundAccountCapital fundAccountCapital =
        fundAccountCapitalService.addFundAccountCapital(createFundAccountCapitalCommand);
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setLocation(linkTo(methodOn(FundAccountCapitalController.class)
        .readFundAccountCapital(fundAccountCapital.getId())).toUri());
    return new ResponseEntity<Object>(fundAccountCapital, responseHeaders, HttpStatus.CREATED);
  }

  @PutMapping("/{faCapitalId}/set-investment-limit")
  public FundAccountCapitalResource setInvestmentLimit(@PathVariable Long faCapitalId,
      @RequestBody SetInvestmentLimitCommand setInvestmentLimitCommand) {
    FundAccountCapital fundAccountCapital =
        fundAccountCapitalService.setInvestmentLimit(faCapitalId, setInvestmentLimitCommand);
    return readFundAccountCapital(fundAccountCapital.getId());
  }
}

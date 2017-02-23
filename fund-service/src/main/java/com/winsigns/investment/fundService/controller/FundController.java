package com.winsigns.investment.fundService.controller;

import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.winsigns.investment.fundService.command.FundCommand;
import com.winsigns.investment.fundService.model.Fund;
import com.winsigns.investment.fundService.resource.ExternalCapitalAccountResourceAssembler;
import com.winsigns.investment.fundService.resource.FundAccountResourceAssembler;
import com.winsigns.investment.fundService.resource.FundResource;
import com.winsigns.investment.fundService.resource.FundResourceAssembler;
import com.winsigns.investment.fundService.service.FundService;

@RestController
@RequestMapping(path = "/funds", produces = { HAL_JSON_VALUE, APPLICATION_JSON_VALUE, APPLICATION_JSON_UTF8_VALUE })

public class FundController {

	@Autowired
	private FundService fundService;

	@RequestMapping(method = RequestMethod.GET)
	public Resources<FundResource> readFunds() {
		Link link = linkTo(FundController.class).withSelfRel();
		return new Resources<FundResource>(new FundResourceAssembler().toResources(fundService.findAllFunds()), link);
	}

	@RequestMapping(value = "/{fundId}", method = RequestMethod.GET)
	public FundResource readFund(@PathVariable Long fundId) {
		Fund fund = fundService.findOne(fundId);
		FundResource fundResource = new FundResourceAssembler().toResource(fund);

		fundResource.add("fundAccounts", new FundAccountResourceAssembler().toResources(fund.getFundAccounts()));
		fundResource.add("externalCapitalAccounts",
				new ExternalCapitalAccountResourceAssembler().toResources(fund.getExternalCapitalAccounts()));

		return fundResource;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createFund(@RequestBody FundCommand fundCommand) {

		HttpHeaders responseHeaders = new HttpHeaders();
		Fund fund = fundService.addFund(fundCommand);
		responseHeaders.setLocation(linkTo(methodOn(FundController.class).readFund(fund.getId())).toUri());

		return new ResponseEntity<Object>(fund, responseHeaders, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{fundId}", method = RequestMethod.PUT)
	public FundResource updateFund(@PathVariable("fundId") Long fundId, @RequestBody FundCommand fund) {
		return new FundResourceAssembler().toResource(fundService.updateFund(fundId, fund));
	}

	@RequestMapping(value = "/{fundId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteFund(@PathVariable("fundId") Long id) {
		fundService.deleteFund(id);
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}
}

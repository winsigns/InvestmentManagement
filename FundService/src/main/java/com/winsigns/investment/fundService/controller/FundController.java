package com.winsigns.investment.fundService.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
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
import com.winsigns.investment.fundService.resource.FundResource;
import com.winsigns.investment.fundService.resource.FundResourceAssembler;
import com.winsigns.investment.fundService.service.FundService;

@RestController
@ExposesResourceFor(Fund.class)
@RequestMapping("/funds")
public class FundController {

	@Autowired
	private FundService fundService;

	@Autowired
	private EntityLinks entityLinks;

	@RequestMapping(method = RequestMethod.GET)
	public Resources<FundResource> readFunds() {
		Link link = linkTo(FundController.class).withSelfRel();
		return new Resources<FundResource>(new FundResourceAssembler().toResources(fundService.findAllFunds()), link);
	}

	@RequestMapping(value = "/{fundId}", method = RequestMethod.GET)
	public FundResource readFund(@PathVariable Long fundId) {
		return new FundResourceAssembler().toResource(fundService.findOne(fundId));
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createFund(@RequestBody FundCommand fund) {

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setLocation(entityLinks.linkForSingleResource(Fund.class, fundService.addFund(fund)).toUri());
		return new ResponseEntity<Object>(responseHeaders, HttpStatus.CREATED);
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

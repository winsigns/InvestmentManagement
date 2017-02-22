package com.winsigns.investment.fundService.controller;

import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.winsigns.investment.fundService.command.PortfolioCommand;
import com.winsigns.investment.fundService.model.Portfolio;
import com.winsigns.investment.fundService.resource.PortfolioResource;
import com.winsigns.investment.fundService.resource.PortfolioResourceAssembler;
import com.winsigns.investment.fundService.service.PortfolioService;

@RestController
@RequestMapping(path = "/funds/{fundId}/fundAccounts/{fundAccountId}/portfolios", produces = {HAL_JSON_VALUE, APPLICATION_JSON_VALUE, APPLICATION_JSON_UTF8_VALUE})
public class PortfolioController {

	@Autowired
	PortfolioService portfolioService;

	@RequestMapping(method = RequestMethod.GET)
	public Resources<PortfolioResource> readPortfolios(@PathVariable Long fundId, @PathVariable Long fundAccountId) {
		Link link = linkTo(PortfolioController.class, fundId, fundAccountId).withSelfRel();
		return new Resources<PortfolioResource>(
				new PortfolioResourceAssembler().toResources(portfolioService.findByFundAccountId(fundAccountId)),
				link);
	}

	@RequestMapping(value = "/{portfolioId}", method = RequestMethod.GET)
	public PortfolioResource readPortfolio(@PathVariable Long fundId, @PathVariable Long fundAccountId,
			@PathVariable Long portfolioId) {
		return new PortfolioResourceAssembler().toResource(portfolioService.findOne(portfolioId));
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createPortfolio(@PathVariable Long fundId, @PathVariable Long fundAccountId,
			@RequestBody PortfolioCommand portfolioCommand) {

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setLocation(linkTo(methodOn(PortfolioController.class).readPortfolio(fundId, fundAccountId,
				portfolioService.addPortfolio(fundAccountId, portfolioCommand).getId())).toUri());
		return new ResponseEntity<Object>(responseHeaders, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{portfolioId}", method = RequestMethod.PUT)
	public PortfolioResource updatePortfolio(@PathVariable("portfolioId") Long portfolioId,
			@RequestBody PortfolioCommand portfolioCommand) {
		return new PortfolioResourceAssembler()
				.toResource(portfolioService.updatePortfolio(portfolioId, portfolioCommand));
	}

	@RequestMapping(value = "/{portfolioId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deletePortfolio(@PathVariable("portfolioId") Long portfolioId) {
		portfolioService.deletePortfolio(portfolioId);
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}

}

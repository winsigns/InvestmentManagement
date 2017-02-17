package com.winsigns.investment.fundService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
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

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/funds/{fundId}/fundAccounts/{fundAccountId}/portfolios")
public class PortfolioController {

	@Autowired
	PortfolioService portfolioService;
	
	@Autowired
    private EntityLinks entityLinks;
	
	@RequestMapping(method = RequestMethod.GET)
    public Resources<PortfolioResource> readFundAccounts(@PathVariable Long fundAccountId) {
        Link link = linkTo(PortfolioController.class, fundAccountId).withSelfRel();
        return new Resources<PortfolioResource>(
                new PortfolioResourceAssembler().toResources(portfolioService.findByFundAccountId(fundAccountId)),
                link
        );
    }
	
	@RequestMapping(value = "/{fundAccountId}", method = RequestMethod.GET)
    public PortfolioResource readFundAccount(@PathVariable Long fundAccountId) {
		return new PortfolioResourceAssembler().toResource(portfolioService.findOne(fundAccountId));
    }
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createPortfolio(
			@PathVariable("fundAccountId") Long fundAccountId,
			@RequestBody PortfolioCommand portfolioCommand){
		
		HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(entityLinks.linkForSingleResource(Portfolio.class, portfolioService.addPortfolio(fundAccountId, portfolioCommand)).toUri());
        return new ResponseEntity<Object>(responseHeaders, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/{portfolioId}", method = RequestMethod.PUT)
	public PortfolioResource updatePortfolio(
			@PathVariable("portfolioId") Long portfolioId,
			@RequestBody PortfolioCommand portfolioCommand){		
		return new PortfolioResourceAssembler().toResource(portfolioService.updatePortfolio(portfolioId, portfolioCommand));
	}
	
	@RequestMapping(value = "/{portfolioId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deletePortfolio(
			@PathVariable("portfolioId") Long portfolioId){
		portfolioService.deletePortfolio(portfolioId);
	    return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}

}

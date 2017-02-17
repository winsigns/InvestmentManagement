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

import com.winsigns.investment.fundService.command.FundAccountCommand;
import com.winsigns.investment.fundService.model.FundAccount;
import com.winsigns.investment.fundService.resource.FundAccountResource;
import com.winsigns.investment.fundService.resource.FundAccountResourceAssembler;
import com.winsigns.investment.fundService.service.FundAccountService;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/funds/{fundId}/fundAccounts")
public class FundAccountController {

	@Autowired
	FundAccountService fundAccountService;
	
	@Autowired
    private EntityLinks entityLinks;
	
	@RequestMapping(method = RequestMethod.GET)
    public Resources<FundAccountResource> readFundAccounts(@PathVariable Long fundId) {
        Link link = linkTo(FundAccountController.class, fundId).withSelfRel();
        return new Resources<FundAccountResource>(
                new FundAccountResourceAssembler().toResources(fundAccountService.findByFundId(fundId)),
                link
        );
    }
	
	@RequestMapping(value = "/{fundAccountId}", method = RequestMethod.GET)
    public FundAccountResource readFundAccount(@PathVariable Long fundAccountId) {
		return new FundAccountResourceAssembler().toResource(fundAccountService.findOne(fundAccountId));
    }
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createFundAccount(
			@PathVariable("fundId") Long fundId,
			@RequestBody FundAccountCommand fundAccount){
		
		HttpHeaders responseHeaders = new HttpHeaders();
		fundAccountService.addFundAccount(fundId, fundAccount);
        responseHeaders.setLocation(entityLinks.linkForSingleResource(FundAccount.class, fundAccountService.addFundAccount(fundId, fundAccount)).toUri());
        return new ResponseEntity<Object>(responseHeaders, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/{fundAccountId}", method = RequestMethod.PUT)
	public FundAccountResource updateFundAccount(
			@PathVariable("fundAccountId") Long fundAccountId,
			@RequestBody FundAccountCommand fundAccount){		
		return new FundAccountResourceAssembler().toResource(fundAccountService.updateFundAccount(fundAccountId, fundAccount));
	}
	
	@RequestMapping(value = "/{fundAccountId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteFundAccount(
			@PathVariable("fundAccountId") Long fundAccountId){
		fundAccountService.deleteFundAccount(fundAccountId);
	    return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}

}

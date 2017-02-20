package com.winsigns.investment.inventoryService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.winsigns.investment.inventoryService.command.CapitalChangeCommand;
import com.winsigns.investment.inventoryService.model.FundAccountCapital;
import com.winsigns.investment.inventoryService.resource.FundAccountCapitalResource;
import com.winsigns.investment.inventoryService.resource.FundAccountCapitalResourceAssembler;
import com.winsigns.investment.inventoryService.service.FundAccountCapitalService;

@RestController
@ExposesResourceFor(FundAccountCapital.class)
@RequestMapping("/fundAccounts/{fundAccountId}/fundAccountCapitals")
public class FundAccountCapitalController {

	@Autowired
	FundAccountCapitalService fundAccountCapitalService;

	// 从资金账户分配资金到产品账户
	@RequestMapping(value = "/assignFrom/{externalCapitalAccountId}", method = RequestMethod.POST)
	public FundAccountCapitalResource assignToFundAccountFromCapitalAccount(@PathVariable Long fundAccountId,
			@PathVariable Long externalCapitalAccountId, @RequestBody CapitalChangeCommand capitalChangeCommand) {
		return new FundAccountCapitalResourceAssembler().toResource(fundAccountCapitalService
				.assignToFundAccountFromCapitalAccount(fundAccountId, externalCapitalAccountId, capitalChangeCommand));
	}

	// 从产品账户归还资金到资金账户
	@RequestMapping(value = "/assignTo/{externalCapitalAccountId}", method = RequestMethod.POST)
	public FundAccountCapitalResource assignToCapitalAccountFromFundAccount(@PathVariable Long fundAccountId,
			@PathVariable Long externalCapitalAccountId, @RequestBody CapitalChangeCommand capitalChangeCommand) {
		return new FundAccountCapitalResourceAssembler().toResource(fundAccountCapitalService
				.assignToCapitalAccountFromFundAccount(externalCapitalAccountId, fundAccountId, capitalChangeCommand));
	}

	// 从产品账户让渡资金到产品账户
	@RequestMapping(value = "/transfer/{matchfundAccountId}", method = RequestMethod.POST)
	public FundAccountCapitalResource transfer(@PathVariable Long fundAccountId, @PathVariable Long matchfundAccountId,
			@RequestBody CapitalChangeCommand capitalChangeCommand) {
		return new FundAccountCapitalResourceAssembler().toResource(
				fundAccountCapitalService.transfer(fundAccountId, matchfundAccountId, capitalChangeCommand));
	}
}

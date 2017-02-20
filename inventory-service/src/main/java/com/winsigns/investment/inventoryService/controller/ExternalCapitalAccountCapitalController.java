package com.winsigns.investment.inventoryService.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.winsigns.investment.inventoryService.command.CapitalChangeCommand;
import com.winsigns.investment.inventoryService.model.ExternalCapitalAccountCapital;
import com.winsigns.investment.inventoryService.resource.ExternalCapitalAccountCapitalResource;
import com.winsigns.investment.inventoryService.resource.ExternalCapitalAccountCapitalResourceAssembler;
import com.winsigns.investment.inventoryService.service.ExternalCapitalAccountCapitalService;

@RestController
@ExposesResourceFor(ExternalCapitalAccountCapital.class)
@RequestMapping("/externalCapitalAccountCapitals")
public class ExternalCapitalAccountCapitalController {

	@Autowired
	ExternalCapitalAccountCapitalService externalCapitalAccountCapitalService;

	// 转入资金
	@RequestMapping(value = "/{externalCapitalAccountId}/transferTo", method = RequestMethod.POST)
	public ExternalCapitalAccountCapitalResource transferToExternalCapitalAccount(
			@PathVariable Long externalCapitalAccountId, @RequestBody CapitalChangeCommand capitalChangeCommand) {
		return new ExternalCapitalAccountCapitalResourceAssembler().toResource(externalCapitalAccountCapitalService
				.transferToExternalCapitalAccount(externalCapitalAccountId, capitalChangeCommand));
	}

	// 转出资金
	@RequestMapping(value = "/{externalCapitalAccountId}/transferFrom", method = RequestMethod.POST)
	public ExternalCapitalAccountCapitalResource transferFromExternalCapitalAccount(
			@PathVariable Long externalCapitalAccountId, @RequestBody CapitalChangeCommand capitalChangeCommand) {
		return new ExternalCapitalAccountCapitalResourceAssembler().toResource(externalCapitalAccountCapitalService
				.transferFromExternalCapitalAccount(externalCapitalAccountId, capitalChangeCommand));
	}

	// 调拨入金
	@RequestMapping(value = "/{externalCapitalAccountId}/allotInFrom/{matchEexternalCapitalAccountId}", method = RequestMethod.POST)
	public Resources<ExternalCapitalAccountCapitalResource> allotInCapital(@PathVariable Long externalCapitalAccountId,
			@PathVariable Long matchEexternalCapitalAccountId, @RequestBody CapitalChangeCommand capitalChangeCommand) {
		Link link = linkTo(ExternalCapitalAccountCapitalController.class).withSelfRel();
		return new Resources<ExternalCapitalAccountCapitalResource>(
				new ExternalCapitalAccountCapitalResourceAssembler().toResources(externalCapitalAccountCapitalService
						.allot(externalCapitalAccountId, matchEexternalCapitalAccountId, capitalChangeCommand)),
				link);
	}

	// 调拨出金
	@RequestMapping(value = "/{externalCapitalAccountId}/allotOutTo/{matchEexternalCapitalAccountId}", method = RequestMethod.POST)
	public Resources<ExternalCapitalAccountCapitalResource> allotOutCapital(@PathVariable Long externalCapitalAccountId,
			@RequestParam Long matchEexternalCapitalAccountId, @RequestBody CapitalChangeCommand capitalChangeCommand) {
		Link link = linkTo(ExternalCapitalAccountCapitalController.class).withSelfRel();
		return new Resources<ExternalCapitalAccountCapitalResource>(
				new ExternalCapitalAccountCapitalResourceAssembler().toResources(externalCapitalAccountCapitalService
						.allot(matchEexternalCapitalAccountId, externalCapitalAccountId, capitalChangeCommand)),
				link);
	}

	// 查询资金
	@RequestMapping(value = "/{externalCapitalAccountId}", method = RequestMethod.GET)
	public Resources<ExternalCapitalAccountCapitalResource> readExternalCapitalAccountCapitals(
			@PathVariable Long externalCapitalAccountId) {
		Link link = linkTo(ExternalCapitalAccountCapitalController.class).withSelfRel();
		return new Resources<ExternalCapitalAccountCapitalResource>(
				new ExternalCapitalAccountCapitalResourceAssembler().toResources(externalCapitalAccountCapitalService
						.readExternalCapitalAccountCapitals(externalCapitalAccountId)),
				link);
	}
}

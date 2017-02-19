package com.winsigns.investment.inventoryService.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

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

import com.winsigns.investment.inventoryService.command.ExternalTradeAccountCommand;
import com.winsigns.investment.inventoryService.resource.ExternalTradeAccountResource;
import com.winsigns.investment.inventoryService.resource.ExternalTradeAccountResourceAssembler;
import com.winsigns.investment.inventoryService.service.ExternalTradeAccountService;

@RestController
@RequestMapping("/funds/{fundId}/externalCapitalAccounts/{externalCapitalAccountId}/externalTradeAccounts")
public class ExternalTradeAccountController {

	@Autowired
	ExternalTradeAccountService externalTradeAccountService;

	@RequestMapping(method = RequestMethod.GET)
	public Resources<ExternalTradeAccountResource> readExternalTradeAccounts(@PathVariable Long fundId,
			@PathVariable Long externalCapitalAccountId) {
		Link link = linkTo(ExternalTradeAccountController.class, fundId, externalCapitalAccountId).withSelfRel();
		return new Resources<ExternalTradeAccountResource>(new ExternalTradeAccountResourceAssembler().toResources(
				externalTradeAccountService.findByExternalCapitalAccountId(externalCapitalAccountId)), link);
	}

	@RequestMapping(value = "/{externalTradeAccountId}", method = RequestMethod.GET)
	public ExternalTradeAccountResource readExternalTradeAccount(@PathVariable Long fundId,
			@PathVariable Long externalCapitalAccountId, @PathVariable Long externalTradeAccountId) {
		return new ExternalTradeAccountResourceAssembler()
				.toResource(externalTradeAccountService.findOne(externalTradeAccountId));
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> crreateExternalTradeAccount(@PathVariable Long fundId,
			@PathVariable Long externalCapitalAccountId,
			@RequestBody ExternalTradeAccountCommand externalTradeAccountCommand) {

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders
				.setLocation(
						linkTo(methodOn(ExternalTradeAccountController.class)
								.readExternalTradeAccount(fundId, externalCapitalAccountId,
										externalTradeAccountService.addExternalTradeAccount(fundId,
												externalCapitalAccountId, externalTradeAccountCommand).getId()))
														.toUri());
		return new ResponseEntity<Object>(responseHeaders, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{externalTradeAccountId}", method = RequestMethod.PUT)
	public ExternalTradeAccountResource updateExternalTradeAccount(@PathVariable Long externalTradeAccountId,
			@RequestBody ExternalTradeAccountCommand externalTradeAccountCommand) {
		return new ExternalTradeAccountResourceAssembler().toResource(externalTradeAccountService
				.updateExternalTradeAccount(externalTradeAccountId, externalTradeAccountCommand));
	}

	@RequestMapping(value = "/{externalTradeAccountId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteExternalTradeAccount(@PathVariable Long externalTradeAccountId) {
		externalTradeAccountService.deleteExternalTradeAccount(externalTradeAccountId);
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}
}

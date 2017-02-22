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

import com.winsigns.investment.fundService.command.ExternalCapitalAccountCommand;
import com.winsigns.investment.fundService.resource.ExternalCapitalAccountResource;
import com.winsigns.investment.fundService.resource.ExternalCapitalAccountResourceAssembler;
import com.winsigns.investment.fundService.service.ExternalCapitalAccountService;

@RestController
@RequestMapping(path = "/funds/{fundId}/externalCapitalAccounts", produces = { HAL_JSON_VALUE, APPLICATION_JSON_VALUE,
		APPLICATION_JSON_UTF8_VALUE })
public class ExternalCapitalAccountController {

	@Autowired
	ExternalCapitalAccountService externalCapitalAccountService;

	@RequestMapping(method = RequestMethod.GET)
	public Resources<ExternalCapitalAccountResource> readExternalCapitalAccounts(@PathVariable Long fundId) {
		Link link = linkTo(ExternalCapitalAccountController.class, fundId).withSelfRel();
		return new Resources<ExternalCapitalAccountResource>(new ExternalCapitalAccountResourceAssembler()
				.toResources(externalCapitalAccountService.findByFundId(fundId)), link);
	}

	@RequestMapping(value = "/{externalCapitalAccountId}", method = RequestMethod.GET)
	public ExternalCapitalAccountResource readExternalCapitalAccount(@PathVariable Long fundId,
			@PathVariable Long externalCapitalAccountId) {
		return new ExternalCapitalAccountResourceAssembler()
				.toResource(externalCapitalAccountService.findOne(externalCapitalAccountId));
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> crreateExternalCapitalAccount(@PathVariable Long fundId,
			@RequestBody ExternalCapitalAccountCommand externalCapitalAccountCommand) {

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders
				.setLocation(
						linkTo(methodOn(ExternalCapitalAccountController.class).readExternalCapitalAccount(fundId,
								externalCapitalAccountService
										.addExternalCapitalAccount(fundId, externalCapitalAccountCommand).getId()))
												.toUri());
		return new ResponseEntity<Object>(responseHeaders, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{externalCapitalAccountId}", method = RequestMethod.PUT)
	public ExternalCapitalAccountResource updateExternalCapitalAccount(@PathVariable Long externalCapitalAccountId,
			@RequestBody ExternalCapitalAccountCommand externalCapitalAccountCommand) {
		return new ExternalCapitalAccountResourceAssembler().toResource(externalCapitalAccountService
				.updateExternalCapitalAccount(externalCapitalAccountId, externalCapitalAccountCommand));
	}

	@RequestMapping(value = "/{externalCapitalAccountId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteExternalCapitalAccount(@PathVariable Long externalCapitalAccountId) {
		externalCapitalAccountService.deleteExternalCapitalAccount(externalCapitalAccountId);
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}
}

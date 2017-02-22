package com.winsigns.investment.fundService.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.HashMap;
import java.util.Map;

import org.springframework.hateoas.Resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.winsigns.investment.fundService.controller.ExternalCapitalAccountController;
import com.winsigns.investment.fundService.controller.FundAccountController;
import com.winsigns.investment.fundService.model.Fund;

public class FundResource extends Resource<Fund> {

	private final String name;

	@JsonProperty("_embedded")
	private final Map<String, Object> embedded = new HashMap<String, Object>();

	public FundResource(Fund fund) {
		super(fund);
		this.name = fund.getName();

		this.embedded.put("fundAccounts", new FundAccountResourceAssembler().toResources(fund.getFundAccounts()));
		this.embedded.put("externalCapitalAccounts",
				new ExternalCapitalAccountResourceAssembler().toResources(fund.getExternalCapitalAccounts()));

		Long fundId = fund.getId();
		add(linkTo(methodOn(FundAccountController.class).readFundAccounts(fundId)).withRel("fundAccounts"));
		add(linkTo(methodOn(ExternalCapitalAccountController.class).readExternalCapitalAccounts(fundId))
				.withRel("externalCapitalAccounts"));
	}

	public String getName() {
		return name;
	}
}

package com.winsigns.investment.fundService.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.winsigns.investment.fundService.controller.ExternalCapitalAccountController;
import com.winsigns.investment.fundService.controller.FundAccountController;
import com.winsigns.investment.fundService.hal.HALResponse;
import com.winsigns.investment.fundService.model.Fund;

public class FundResource extends HALResponse<Fund> {

	private final String name;

	public FundResource(Fund fund) {
		super(fund);
		this.name = fund.getName();

		Long fundId = fund.getId();
		add(linkTo(methodOn(FundAccountController.class).readFundAccounts(fundId)).withRel("fundAccounts"));
		add(linkTo(methodOn(ExternalCapitalAccountController.class).readExternalCapitalAccounts(fundId))
				.withRel("externalCapitalAccounts"));
	}

	public String getName() {
		return name;
	}
}

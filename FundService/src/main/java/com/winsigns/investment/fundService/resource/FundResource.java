package com.winsigns.investment.fundService.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;

import com.winsigns.investment.fundService.controller.FundAccountController;
import com.winsigns.investment.fundService.model.Fund;

public class FundResource extends Resource<Fund> {

	private final String name;

	public FundResource(Fund fund) {
		super(fund);
		this.name = fund.getName();
		Long fundId = fund.getId();
		add(linkTo(methodOn(FundAccountController.class).readFundAccounts(fundId)).withRel("fundAccounts"));
	}

	public String getName() {
		return name;
	}
}

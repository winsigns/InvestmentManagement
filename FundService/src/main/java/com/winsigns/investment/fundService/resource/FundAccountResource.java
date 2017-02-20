package com.winsigns.investment.fundService.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;

import com.winsigns.investment.fundService.controller.FundController;
import com.winsigns.investment.fundService.model.FundAccount;

public class FundAccountResource extends Resource<FundAccount> {

	// 名称
	private final String name;

	public FundAccountResource(FundAccount fundAccount) {
		super(fundAccount);
		this.name = fundAccount.getName();

		Long fundId = fundAccount.getFund().getId();
		add(linkTo(methodOn(FundController.class).readFund(fundId)).withRel("fund"));
	}

	public String getName() {
		return name;
	}
}

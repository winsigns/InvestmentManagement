package com.winsigns.investment.fundService.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.winsigns.investment.fundService.controller.FundController;
import com.winsigns.investment.fundService.controller.PortfolioController;
import com.winsigns.investment.fundService.hal.HALResponse;
import com.winsigns.investment.fundService.model.FundAccount;

public class FundAccountResource extends HALResponse<FundAccount> {

	// 名称
	private final String name;

	public FundAccountResource(FundAccount fundAccount) {
		super(fundAccount);
		this.name = fundAccount.getName();

		Long fundId = fundAccount.getFund().getId();
		add(linkTo(methodOn(FundController.class).readFund(fundId)).withRel("fund"));
		add(linkTo(methodOn(PortfolioController.class).readPortfolios(fundId, fundAccount.getId()))
				.withRel("portfolios"));
	}

	public String getName() {
		return name;
	}
}

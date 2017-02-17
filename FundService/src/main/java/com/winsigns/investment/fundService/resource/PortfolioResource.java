package com.winsigns.investment.fundService.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;

import com.winsigns.investment.fundService.controller.FundAccountController;
import com.winsigns.investment.fundService.model.Portfolio;

public class PortfolioResource extends Resource<Portfolio> {

	// 名称
	private final String name;

	public PortfolioResource(Portfolio portfolio) {
		super(portfolio);
		this.name = portfolio.getName();

		Long fundAccountId = portfolio.getFundAccount().getId();
		Long fundId = portfolio.getFundAccount().getFund().getId();
		add(linkTo(methodOn(FundAccountController.class).readFundAccount(fundId, fundAccountId))
				.withRel("fundAccount"));
	}

	public String getName() {
		return name;
	}

}

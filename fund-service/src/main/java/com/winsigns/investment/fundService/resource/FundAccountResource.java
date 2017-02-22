package com.winsigns.investment.fundService.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.winsigns.investment.fundService.controller.FundController;
import com.winsigns.investment.fundService.controller.PortfolioController;
import com.winsigns.investment.fundService.model.FundAccount;

public class FundAccountResource extends Resource<FundAccount> {

	// 名称
	private final String name;

	@JsonUnwrapped
	private final Resources<PortfolioResource> portfolioResources;

	public FundAccountResource(FundAccount fundAccount) {
		super(fundAccount);
		this.name = fundAccount.getName();

		this.portfolioResources = new Resources<PortfolioResource>(
				new PortfolioResourceAssembler().toResources(fundAccount.getPortfolios()));

		Long fundId = fundAccount.getFund().getId();
		add(linkTo(methodOn(FundController.class).readFund(fundId)).withRel("fund"));
		add(linkTo(methodOn(PortfolioController.class).readPortfolios(fundId, fundAccount.getId()))
				.withRel("portfolios"));
	}

	public String getName() {
		return name;
	}
}

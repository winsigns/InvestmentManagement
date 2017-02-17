package com.winsigns.investment.fundService.resource;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.winsigns.investment.fundService.controller.PortfolioController;
import com.winsigns.investment.fundService.model.Portfolio;

public class PortfolioResourceAssembler extends ResourceAssemblerSupport<Portfolio, PortfolioResource> {

    public PortfolioResourceAssembler() {
        super(PortfolioController.class, PortfolioResource.class);
    }

    @Override
    public PortfolioResource toResource(Portfolio portfolio) {
        return createResourceWithId(portfolio.getId(), portfolio, portfolio.getFundAccount().getId(), portfolio.getFundAccount().getFund().getId());
    }

    @Override
    protected PortfolioResource instantiateResource(Portfolio entity) {
        return new PortfolioResource(entity);
    }
}

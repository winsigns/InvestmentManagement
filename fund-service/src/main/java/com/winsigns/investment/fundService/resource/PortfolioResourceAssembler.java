package com.winsigns.investment.fundService.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.core.Relation;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.winsigns.investment.fundService.controller.FundAccountController;
import com.winsigns.investment.fundService.controller.PortfolioController;
import com.winsigns.investment.fundService.model.FundAccount;
import com.winsigns.investment.fundService.model.Portfolio;

public class PortfolioResourceAssembler extends ResourceAssemblerSupport<Portfolio, PortfolioResource> {

    public PortfolioResourceAssembler() {
        super(PortfolioController.class, PortfolioResource.class);
    }

    @Override
    public PortfolioResource toResource(Portfolio portfolio) {

        PortfolioResource portfolioResource = createResourceWithId(portfolio.getId(), portfolio);

        Long fundAccountId = portfolio.getFundAccount().getId();
        portfolioResource.add(linkTo(methodOn(FundAccountController.class).readFundAccount(fundAccountId))
                .withRel(FundAccount.class.getAnnotation(Relation.class).value()));

        return portfolioResource;
    }

    @Override
    protected PortfolioResource instantiateResource(Portfolio entity) {
        return new PortfolioResource(entity);
    }
}

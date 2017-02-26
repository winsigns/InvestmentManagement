package com.winsigns.investment.fundService.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.core.Relation;

import com.winsigns.investment.fundService.controller.FundAccountController;
import com.winsigns.investment.fundService.hal.HALResponse;
import com.winsigns.investment.fundService.model.FundAccount;
import com.winsigns.investment.fundService.model.Portfolio;

public class PortfolioResource extends HALResponse<Portfolio> {

    public PortfolioResource(Portfolio portfolio) {
        super(portfolio);

        Long fundAccountId = portfolio.getFundAccount().getId();
        Long fundId = portfolio.getFundAccount().getFund().getId();
        add(linkTo(methodOn(FundAccountController.class).readFundAccount(fundId, fundAccountId))
                .withRel(FundAccount.class.getAnnotation(Relation.class).value()));
    }

}

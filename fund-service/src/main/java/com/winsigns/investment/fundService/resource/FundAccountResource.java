package com.winsigns.investment.fundService.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.core.Relation;

import com.winsigns.investment.fundService.controller.FundController;
import com.winsigns.investment.fundService.controller.PortfolioController;
import com.winsigns.investment.fundService.hal.HALResponse;
import com.winsigns.investment.fundService.model.Fund;
import com.winsigns.investment.fundService.model.FundAccount;
import com.winsigns.investment.fundService.model.Portfolio;

public class FundAccountResource extends HALResponse<FundAccount> {

    // 名称
    private final String name;

    public FundAccountResource(FundAccount fundAccount) {
        super(fundAccount);
        this.name = fundAccount.getName();

        Long fundId = fundAccount.getFund().getId();
        add(linkTo(methodOn(FundController.class).readFund(fundId))
                .withRel(Fund.class.getAnnotation(Relation.class).value()));
        add(linkTo(methodOn(PortfolioController.class).readPortfolios(fundId, fundAccount.getId()))
                .withRel(Portfolio.class.getAnnotation(Relation.class).collectionRelation()));
    }

    public String getName() {
        return name;
    }
}

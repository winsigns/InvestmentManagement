package com.winsigns.investment.fundService.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.core.Relation;

import com.winsigns.investment.fundService.controller.ExternalTradeAccountController;
import com.winsigns.investment.fundService.controller.FundController;
import com.winsigns.investment.fundService.hal.HALResponse;
import com.winsigns.investment.fundService.model.ExternalCapitalAccount;
import com.winsigns.investment.fundService.model.ExternalTradeAccount;
import com.winsigns.investment.fundService.model.Fund;

public class ExternalCapitalAccountResource extends HALResponse<ExternalCapitalAccount> {

    public ExternalCapitalAccountResource(ExternalCapitalAccount externalCapitalAccount) {

        super(externalCapitalAccount);
        add(linkTo(methodOn(ExternalTradeAccountController.class)
                .readExternalTradeAccounts(externalCapitalAccount.getFund().getId(), externalCapitalAccount.getId()))
                        .withRel(ExternalTradeAccount.class.getAnnotation(Relation.class).collectionRelation()));
        add(linkTo(methodOn(FundController.class).readFund(externalCapitalAccount.getFund().getId()))
                .withRel(Fund.class.getAnnotation(Relation.class).value()));
    }

}

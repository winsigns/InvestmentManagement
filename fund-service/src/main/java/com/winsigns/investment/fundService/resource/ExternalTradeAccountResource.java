package com.winsigns.investment.fundService.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.core.Relation;

import com.winsigns.investment.fundService.controller.ExternalCapitalAccountController;
import com.winsigns.investment.fundService.hal.HALResponse;
import com.winsigns.investment.fundService.model.ExternalCapitalAccount;
import com.winsigns.investment.fundService.model.ExternalTradeAccount;

public class ExternalTradeAccountResource extends HALResponse<ExternalTradeAccount> {

    public ExternalTradeAccountResource(ExternalTradeAccount externalTradeAccount) {
        super(externalTradeAccount);

        Long externalCapitalAccountId = externalTradeAccount.getExternalCapitalAccount().getId();
        add(linkTo(methodOn(ExternalCapitalAccountController.class).readExternalCapitalAccount(
                externalTradeAccount.getExternalCapitalAccount().getFund().getId(), externalCapitalAccountId))
                        .withRel(ExternalCapitalAccount.class.getAnnotation(Relation.class).value()));
    }
}

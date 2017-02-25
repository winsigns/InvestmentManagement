package com.winsigns.investment.fundService.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.core.Relation;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.winsigns.investment.fundService.controller.ExternalCapitalAccountController;
import com.winsigns.investment.fundService.controller.ExternalTradeAccountController;
import com.winsigns.investment.fundService.model.ExternalCapitalAccount;
import com.winsigns.investment.fundService.model.ExternalTradeAccount;

public class ExternalTradeAccountResourceAssembler
        extends ResourceAssemblerSupport<ExternalTradeAccount, ExternalTradeAccountResource> {

    public ExternalTradeAccountResourceAssembler() {
        super(ExternalTradeAccountController.class, ExternalTradeAccountResource.class);
    }

    @Override
    public ExternalTradeAccountResource toResource(ExternalTradeAccount externalTradeAccount) {

        ExternalTradeAccountResource externalTradeAccountResource = createResourceWithId(externalTradeAccount.getId(),
                externalTradeAccount);

        Long externalCapitalAccountId = externalTradeAccount.getExternalCapitalAccount().getId();
        externalTradeAccountResource.add(linkTo(
                methodOn(ExternalCapitalAccountController.class).readExternalCapitalAccount(externalCapitalAccountId))
                        .withRel(ExternalCapitalAccount.class.getAnnotation(Relation.class).value()));

        return externalTradeAccountResource;
    }

    @Override
    protected ExternalTradeAccountResource instantiateResource(ExternalTradeAccount entity) {
        return new ExternalTradeAccountResource(entity);
    }
}

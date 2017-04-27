package com.winsigns.investment.fundService.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.core.Relation;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.winsigns.investment.fundService.controller.ExternalCapitalAccountController;
import com.winsigns.investment.fundService.controller.FundController;
import com.winsigns.investment.fundService.model.ExternalCapitalAccount;

public class ExternalCapitalAccountResourceAssembler
    extends ResourceAssemblerSupport<ExternalCapitalAccount, ExternalCapitalAccountResource> {

  public ExternalCapitalAccountResourceAssembler() {
    super(ExternalCapitalAccountController.class, ExternalCapitalAccountResource.class);
  }

  @Override
  public ExternalCapitalAccountResource toResource(ExternalCapitalAccount externalCapitalAccount) {

    ExternalCapitalAccountResource externalCapitalAccountResource =
        createResourceWithId(externalCapitalAccount.getId(), externalCapitalAccount);

    externalCapitalAccountResource.add(
        linkTo(methodOn(FundController.class).readFund(externalCapitalAccount.getFund().getId()))
            .withRel(FundResource.class.getAnnotation(Relation.class).value()));

    externalCapitalAccountResource.add(linkTo(methodOn(ExternalCapitalAccountController.class)
        .readExternalTradeAccounts(externalCapitalAccount.getId())).withRel(
            ExternalTradeAccountResource.class.getAnnotation(Relation.class).collectionRelation()));

    return externalCapitalAccountResource;
  }

  @Override
  protected ExternalCapitalAccountResource instantiateResource(ExternalCapitalAccount entity) {
    return new ExternalCapitalAccountResource(entity);
  }
}

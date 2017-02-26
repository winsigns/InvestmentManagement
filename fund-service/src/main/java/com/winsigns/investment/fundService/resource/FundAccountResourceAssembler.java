package com.winsigns.investment.fundService.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.winsigns.investment.fundService.controller.FundAccountController;
import com.winsigns.investment.fundService.controller.FundController;
import com.winsigns.investment.fundService.model.Fund;
import com.winsigns.investment.fundService.model.FundAccount;
import com.winsigns.investment.fundService.model.Portfolio;
import org.springframework.hateoas.core.Relation;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class FundAccountResourceAssembler extends
    ResourceAssemblerSupport<FundAccount, FundAccountResource> {

  public FundAccountResourceAssembler() {
    super(FundAccountController.class, FundAccountResource.class);
  }

  @Override
  public FundAccountResource toResource(FundAccount fundAccount) {

    FundAccountResource fundAccountResource = createResourceWithId(fundAccount.getId(),
        fundAccount);

    Long fundId = fundAccount.getFund().getId();
    fundAccountResource.add(linkTo(methodOn(FundController.class).readFund(fundId))
        .withRel(Fund.class.getAnnotation(Relation.class).value()));

    fundAccountResource
        .add(linkTo(methodOn(FundController.class).readPortfolios(fundId, fundAccount.getId()))
            .withRel(Portfolio.class.getAnnotation(Relation.class).collectionRelation()));

    return fundAccountResource;
  }

  @Override
  protected FundAccountResource instantiateResource(FundAccount entity) {
    return new FundAccountResource(entity);
  }
}

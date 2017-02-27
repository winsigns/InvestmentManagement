package com.winsigns.investment.inventoryService.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.core.Relation;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.winsigns.investment.inventoryService.command.SetInvestmentLimitCommand;
import com.winsigns.investment.inventoryService.controller.FundAccountCapitalController;
import com.winsigns.investment.inventoryService.controller.FundAccountCapitalDetailController;
import com.winsigns.investment.inventoryService.model.FundAccountCapital;
import com.winsigns.investment.inventoryService.model.FundAccountCapitalDetail;

public class FundAccountCapitalResourceAssembler
    extends ResourceAssemblerSupport<FundAccountCapital, FundAccountCapitalResource> {

  public FundAccountCapitalResourceAssembler() {
    super(FundAccountCapitalController.class, FundAccountCapitalResource.class);
  }

  @Override
  public FundAccountCapitalResource toResource(FundAccountCapital fundAccountCapital) {
    FundAccountCapitalResource fundAccountCapitalResource =
        createResourceWithId(fundAccountCapital.getId(), fundAccountCapital);

    fundAccountCapitalResource.add(linkTo(methodOn(FundAccountCapitalDetailController.class)
        .readFundAccountCapitalDetail(fundAccountCapital.getId())).withRel(
            FundAccountCapitalDetail.class.getAnnotation(Relation.class).collectionRelation()));
    fundAccountCapitalResource.add(linkTo(methodOn(FundAccountCapitalController.class)
        .setInvestmentLimit(fundAccountCapital.getId(), new SetInvestmentLimitCommand()))
            .withRel("set-investment-limit"));

    return fundAccountCapitalResource;
  }

  @Override
  protected FundAccountCapitalResource instantiateResource(FundAccountCapital entity) {
    return new FundAccountCapitalResource(entity);
  }
}

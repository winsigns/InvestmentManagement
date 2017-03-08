package com.winsigns.investment.webgateway.resource;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.winsigns.investment.webgateway.controller.FundController;
import com.winsigns.investment.webgateway.model.Fund;

public class FundResourceAssembler extends ResourceAssemblerSupport<Fund, FundResource> {
  public FundResourceAssembler() {
    super(FundController.class, FundResource.class);
  }

  @Override
  public FundResource toResource(Fund fund) {
    // TODO Auto-generated method stub
    FundResource resource = createResourceWithId(fund.getId(), fund);

    Long fundId = fund.getId();
    System.out.print(fundId);
    // resource.add(linkTo(methodOn(FundController.class).readFunds()(fundId))
    // .withRel(FundAccount.class.getAnnotation(Relation.class).collectionRelation()));
    //
    // resource.add(linkTo(methodOn(FundController.class).readExternalCapitalAccounts(fundId))
    // .withRel(ExternalCapitalAccount.class.getAnnotation(Relation.class).collectionRelation()));

    return resource;
  }
}

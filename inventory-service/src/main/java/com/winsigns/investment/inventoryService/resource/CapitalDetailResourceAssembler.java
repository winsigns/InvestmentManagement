package com.winsigns.investment.inventoryService.resource;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.winsigns.investment.inventoryService.controller.CapitalDetailController;
import com.winsigns.investment.inventoryService.model.CapitalDetail;

public class CapitalDetailResourceAssembler
    extends ResourceAssemblerSupport<CapitalDetail, CapitalDetailResource> {

  public CapitalDetailResourceAssembler() {
    super(CapitalDetailController.class, CapitalDetailResource.class);
  }

  @Override
  public CapitalDetailResource toResource(CapitalDetail capitalDetail) {

    CapitalDetailResource capitalDetailResource =
        createResourceWithId(capitalDetail.getId(), capitalDetail);


    return capitalDetailResource;
  }

  @Override
  protected CapitalDetailResource instantiateResource(CapitalDetail entity) {
    return new CapitalDetailResource(entity);
  }
}

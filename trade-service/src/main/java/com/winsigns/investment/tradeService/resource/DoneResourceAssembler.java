package com.winsigns.investment.tradeService.resource;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.winsigns.investment.tradeService.controller.DoneController;
import com.winsigns.investment.tradeService.model.Done;

public class DoneResourceAssembler extends ResourceAssemblerSupport<Done, DoneResource> {

  public DoneResourceAssembler() {
    super(DoneController.class, DoneResource.class);
  }

  @Override
  public DoneResource toResource(Done done) {
    return createResourceWithId(done.getId(), done);
  }

  @Override
  protected DoneResource instantiateResource(Done entity) {
    return new DoneResource(entity);
  }
}

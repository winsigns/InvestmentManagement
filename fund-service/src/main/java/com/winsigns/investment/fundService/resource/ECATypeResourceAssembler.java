package com.winsigns.investment.fundService.resource;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.winsigns.investment.fundService.constant.ExternalCapitalAccountType;
import com.winsigns.investment.fundService.controller.ExternalCapitalAccountController;

public class ECATypeResourceAssembler
    extends ResourceAssemblerSupport<ExternalCapitalAccountType, ECATypeResource> {

  public ECATypeResourceAssembler() {
    super(ExternalCapitalAccountController.class, ECATypeResource.class);
  }

  @Override
  public ECATypeResource toResource(ExternalCapitalAccountType type) {

    ECATypeResource resource = createResourceWithId(type, type);

    return resource;
  }

  @Override
  protected ECATypeResource instantiateResource(ExternalCapitalAccountType entity) {
    return new ECATypeResource(entity);
  }
}

package com.winsigns.investment.tradeService.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.core.Relation;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.winsigns.investment.tradeService.controller.EntrustController;
import com.winsigns.investment.tradeService.model.Done;
import com.winsigns.investment.tradeService.model.Entrust;

public class EntrustResourceAssembler extends ResourceAssemblerSupport<Entrust, EntrustResource> {

  public EntrustResourceAssembler() {
    super(EntrustController.class, EntrustResource.class);
  }

  @Override
  public EntrustResource toResource(Entrust entrust) {
    EntrustResource resource = createResourceWithId(entrust.getId(), entrust);

    resource.add(linkTo(methodOn(EntrustController.class).readDones(entrust.getId()))
        .withRel(Done.class.getAnnotation(Relation.class).collectionRelation()));
    // 准备各项过滤条件的Link

    return resource;
  }

  @Override
  protected EntrustResource instantiateResource(Entrust entity) {
    return new EntrustResource(entity);
  }
}

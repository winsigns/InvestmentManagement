package com.winsigns.investment.inventoryService.resource;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.winsigns.investment.inventoryService.controller.PositionController;
import com.winsigns.investment.inventoryService.model.Position;

public class PositionResourceAssembler
    extends ResourceAssemblerSupport<Position, PositionResource> {

  public PositionResourceAssembler() {
    super(PositionController.class, PositionResource.class);
  }

  @Override
  public PositionResource toResource(Position position) {
    PositionResource positionResource = createResourceWithId(position.getId(), position);

    return positionResource;
  }

  @Override
  protected PositionResource instantiateResource(Position entity) {
    return new PositionResource(entity);
  }

}

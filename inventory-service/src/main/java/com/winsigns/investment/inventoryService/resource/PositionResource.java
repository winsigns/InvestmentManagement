package com.winsigns.investment.inventoryService.resource;

import org.springframework.hateoas.core.Relation;

import com.winsigns.investment.framework.hal.HALResponse;
import com.winsigns.investment.inventoryService.model.Position;

@Relation(value = "position", collectionRelation = "positions")
public class PositionResource extends HALResponse<Position> {

  public PositionResource(Position position) {
    super(position);
  }

}

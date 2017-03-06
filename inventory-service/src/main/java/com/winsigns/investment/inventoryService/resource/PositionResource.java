package com.winsigns.investment.inventoryService.resource;

import com.winsigns.investment.framework.hal.HALResponse;
import com.winsigns.investment.inventoryService.model.Position;

public class PositionResource extends HALResponse<Position> {

  public PositionResource(Position position) {
    super(position);
  }

}

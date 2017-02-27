package com.winsigns.investment.inventoryService.resource;

import com.winsigns.investment.inventoryService.hal.HALResponse;
import com.winsigns.investment.inventoryService.model.ECACashPool;

public class ECACashPoolResource extends HALResponse<ECACashPool> {

  public ECACashPoolResource(ECACashPool ecaCashPool) {
    super(ecaCashPool);
  }

}

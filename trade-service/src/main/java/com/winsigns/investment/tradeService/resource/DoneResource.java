package com.winsigns.investment.tradeService.resource;

import org.springframework.hateoas.core.Relation;

import com.winsigns.investment.framework.hal.HALResponse;
import com.winsigns.investment.tradeService.model.Done;

@Relation(value = "done", collectionRelation = "dones")
public class DoneResource extends HALResponse<Done> {

  public DoneResource(Done done) {
    super(done);

  }

}

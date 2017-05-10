package com.winsigns.investment.generalService.resource;

import org.springframework.hateoas.core.Relation;

import com.winsigns.investment.framework.hal.HALResponse;
import com.winsigns.investment.generalService.model.OperatorSequence;

@Relation(value = "operator-sequence", collectionRelation = "operator-sequences")
public class OperatorSequenceResource extends HALResponse<OperatorSequence> {

  public OperatorSequenceResource(OperatorSequence resource) {
    super(resource);
  }

}

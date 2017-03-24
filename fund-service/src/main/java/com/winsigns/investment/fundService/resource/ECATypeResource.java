package com.winsigns.investment.fundService.resource;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.winsigns.investment.fundService.constant.ExternalCapitalAccountType;

import lombok.Getter;

@Relation(value = "eca-type", collectionRelation = "eca-types")
public class ECATypeResource extends ResourceSupport {

  @Getter
  private final String name;

  @Getter
  private final String displayname;

  public ECATypeResource(ExternalCapitalAccountType ecaType) {

    this.name = ecaType.name();

    this.displayname = ecaType.i18n();
  }

}

package com.winsigns.investment.fundService.resource;

import org.springframework.hateoas.core.Relation;

import com.winsigns.investment.framework.hal.HALResponse;
import com.winsigns.investment.fundService.model.ExternalCapitalAccount;

import lombok.Getter;

@Relation(value = "external-capital-account", collectionRelation = "external-capital-accounts")
public class ExternalCapitalAccountResource extends HALResponse<ExternalCapitalAccount> {

  @Getter
  private final String ecaTypeShowName;

  public ExternalCapitalAccountResource(ExternalCapitalAccount externalCapitalAccount) {

    super(externalCapitalAccount);

    this.ecaTypeShowName = externalCapitalAccount.getAccountType().i18n();
  }

}

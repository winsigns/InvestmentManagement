package com.winsigns.investment.fundService.resource;

import com.winsigns.investment.framework.hal.HALResponse;
import com.winsigns.investment.fundService.model.ExternalCapitalAccount;

import lombok.Getter;

public class ExternalCapitalAccountResource extends HALResponse<ExternalCapitalAccount> {

  @Getter
  private final String ecaTypeShowName;

  public ExternalCapitalAccountResource(ExternalCapitalAccount externalCapitalAccount) {

    super(externalCapitalAccount);

    this.ecaTypeShowName = externalCapitalAccount.getAccountType().i18n();
  }

}

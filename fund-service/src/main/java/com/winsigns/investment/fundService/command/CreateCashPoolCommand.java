package com.winsigns.investment.fundService.command;

import com.winsigns.investment.fundService.constant.CurrencyCode;

import lombok.Data;

@Data
public class CreateCashPoolCommand {

  private CurrencyCode currency;

  private Long externalCapitalAccountId;

}

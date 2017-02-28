package com.winsigns.investment.tradeService.service;

import com.winsigns.investment.tradeService.command.CommitInstructionCommand;

public abstract class TradeServiceBase {

  protected CommitInstructionCommand metadata;

  public void init(CommitInstructionCommand commitInstructionCommand) {
    this.metadata = commitInstructionCommand;
  }

  abstract Double calculateRequiredCapital();

  abstract Integer calculateRequiredPosition();

  public abstract String getName();

}

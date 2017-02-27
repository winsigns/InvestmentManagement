package com.winsigns.investment.fundService.command;

public class CreatePortfolioCommand {
  // 组合名称
  private String name;

  public String getName() {
    return this.name;
  }

  public CreatePortfolioCommand setName(String name) {
    this.name = name;
    return this;
  }

}

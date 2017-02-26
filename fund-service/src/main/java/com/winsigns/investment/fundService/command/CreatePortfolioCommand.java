package com.winsigns.investment.fundService.command;

public class CreatePortfolioCommand {

  // 基金产品账户
  private Long fundAccountId;

  // 组合名称
  private String name;

  public String getName() {
    return this.name;
  }

  public CreatePortfolioCommand setName(String name) {
    this.name = name;
    return this;
  }

  public Long getFundAccountId() {
    return fundAccountId;
  }

  public void setFundAccountId(Long fundAccountId) {
    this.fundAccountId = fundAccountId;
  }

}

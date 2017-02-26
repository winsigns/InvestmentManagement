package com.winsigns.investment.fundService.command;

public class CreateFundAccountCommand {

    // 所属基金产品
    private Long fundId;

    // 名称
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getFundId() {
        return fundId;
    }

    public void setFundId(Long fundId) {
        this.fundId = fundId;
    }

}

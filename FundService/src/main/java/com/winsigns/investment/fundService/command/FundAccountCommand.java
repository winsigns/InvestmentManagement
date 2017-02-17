package com.winsigns.investment.fundService.command;

public class FundAccountCommand {
    //名称
    private String name;
    
    //投资经理
    private String investmentManager;
    
    //临时投资经理
    
    
    public String getName() {
        return name;
    }

    public FundAccountCommand setName(String name) {
        this.name = name;
        return this;
    }
    
    public String getInvestmentManager() {
        return investmentManager;
    }

    public FundAccountCommand setInvestmentManager(String investmentManager) {
        this.investmentManager = investmentManager;
        return this;
    } 
}

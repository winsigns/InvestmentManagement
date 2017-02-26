package com.winsigns.investment.fundService.command;

public class UpdatePortfolioCommand {
	//组合名称
	private String name;
    
    public UpdatePortfolioCommand setName(String name){
    	this.name = name;
    	return this;
    }
    
    public String getName(){
    	return this.name;
    }
    
}

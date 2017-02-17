package com.winsigns.investment.fundService.command;

public class PortfolioCommand {
	//组合名称
	private String name;
    
    public PortfolioCommand setName(String name){
    	this.name = name;
    	return this;
    }
    
    public String getName(){
    	return this.name;
    }
    
}

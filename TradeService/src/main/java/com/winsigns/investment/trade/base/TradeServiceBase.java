package com.winsigns.investment.trade.base;

import com.winsigns.investment.trade.model.TradeRepository;

public abstract class TradeServiceBase {
	
	protected TradeRepository repository;
	
	protected TradeMetadata metadata;
	
	protected TradeServiceBase(){
	}
	
	public void init(TradeRepository repository){
		this.repository = repository;
	}
	
	public void init(TradeMetadata metadata){
		this.metadata = metadata;
	}
	
	abstract public void calcQuota();
	
	abstract public boolean checkInstruction();
	
	protected String message;
			
}

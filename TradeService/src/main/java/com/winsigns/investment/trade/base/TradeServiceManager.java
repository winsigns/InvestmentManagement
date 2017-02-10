package com.winsigns.investment.trade.base;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.winsigns.investment.trade.model.TradeRepository;
import com.winsigns.investment.frame.PackageUtil;

@Component
public class TradeServiceManager {
	
	@Autowired
	private TradeRepository tradeRepository;
	
	private List<TradeServiceBase> services = new ArrayList<TradeServiceBase>();
	
	public TradeServiceManager() throws InstantiationException, IllegalAccessException{
		
		List<String> classNames = PackageUtil.getClassName("com.winsigns.investment.trade.detail");
		for (String className : classNames) {  
	        
	        try {
	        	Class< ?> clazz = Class.forName(className);
	        	Object obj = clazz.newInstance();
	        	if(obj.getClass().getSuperclass().equals(TradeServiceBase.class)){
	        		
	        		TradeServiceBase service = (TradeServiceBase)obj;
	        		
	        		services.add(service);
	        	}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}
	
	public List<TradeServiceBase> getAvailTradeService(String investSvc){
		
		List<TradeServiceBase> availServices = new ArrayList<TradeServiceBase>();
		
		for(TradeServiceBase service : services){
			
			Invest invest = service.getClass().getAnnotation(Invest.class);
			
			if(invest != null && invest.value().equals(investSvc)){
				service.init(tradeRepository);
				availServices.add(service);
			}		
		}
		return availServices;	
	}
	
	public List<TradeServiceBase> getAvailTradeService(String investSvc, String securityId){
		
		List<TradeServiceBase> availServices = new ArrayList<TradeServiceBase>();
		
		for(TradeServiceBase service : services){
			
			Invest invest = service.getClass().getAnnotation(Invest.class);
			
			if(invest != null && invest.value().equals(investSvc)){
				service.init(tradeRepository);
				availServices.add(service);
			}		
		}
		return availServices;	
	}
}

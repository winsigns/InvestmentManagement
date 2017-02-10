package com.winsigns.investment.trade.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TradeRepository {

	@Autowired
    public QuotaRepository quotaRepository;
	
	@Autowired
	public QuotaDetailRepository quotaDetailRepository;
}

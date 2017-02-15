package com.winsigns.investment.inventory.capital.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class CapitalRepository {

	@Autowired
    public ExtCapitalAccountRepository extCapitalAccountRepository;
	
	@Autowired
	public FundAccountCapitalRepository fundAccountCapitalRepository;
	
	@Autowired
	public FundAccountCapitalDetailRepository fundAccountCapitalDetailRepository;
	
	@Autowired
	public FundAccountCapitalDetailQuotaRepository fundAccountCapitalDetailQuotaRepository;
	
}

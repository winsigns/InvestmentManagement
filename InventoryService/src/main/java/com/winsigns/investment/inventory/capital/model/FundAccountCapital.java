package com.winsigns.investment.inventory.capital.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity 
//@IdClass(FundAccountCapitalId.class)
@Table(name = "tb_fund_account_capital") 
public class FundAccountCapital {
	
	@EmbeddedId 
	private FundAccountCapitalId fundAccountCapitalId;
	
	@Column(name = "investment_limit", precision = 22,scale = 4)
	private Double investmentLimit;
	
	@Column(name = "occupy_investment_limit", precision = 22,scale = 4)
	private Double occupyInvestmentLimit;
	
//	@Column(name = "capital", precision = 22,scale = 4)
//	private Double capital;
	
//	@Column(name = "avail_capital", precision = 22,scale = 4)
//	private Double availCapital;
//	
//	@Column(name = "desirable_capital", precision = 22,scale = 4)
//	private Double desirableCapital;
}

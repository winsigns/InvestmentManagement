package com.winsigns.investment.trade.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity  
@Table(name = "tb_quota")  
public class Quota {
	
	//指令序号
	@Id
    @GeneratedValue
    private Long id;
	
	//投资组合
	//@Column(name = "portfolio_id", nullable = false, length = 20)
	private String portfolioId;
	
	//投资标的
	//@Column(name = "security_id", nullable = false, length = 20)
	private String securityId;
	
	//外部交易账户
	//@Column(name = "ext_trade_account_id", nullable = true, length = 20)
	private String extTradeAccountId;
	
	//外部资金账户
	//@Column(name = "ext_capital_account_id", nullable = false, length = 20)
	private String extCapitalAccountId;
	
	
	public void setPortfolioId(String portfolioId){
		this.portfolioId = portfolioId;
	}
	
	public String getPortfolioId(){
		return this.portfolioId;
	}
	
	public void setSecurityId(String securityId){
		this.securityId = securityId;
	}
	
	public String getSecurityId(){
		return this.securityId;
	}
	
	public void setExtTradeAccountId(String extTradeAccountId){
		this.extTradeAccountId = extTradeAccountId;
	}
	
	public String getExtTradeAccountId(){
		return this.extTradeAccountId;
	}
	
	public void setExtCapitalAccountId(String extCapitalAccountId){
		this.extCapitalAccountId = extCapitalAccountId;
	}
	
	public String getExtCapitalAccountId(){
		return this.extCapitalAccountId;
	}
	
    public List<QuotaDetail> getDetails() {
        return details;
    }

    public void setDetails(List<QuotaDetail> details) {
        this.details = details;
    }

    @OneToMany(mappedBy = "quota", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    private List<QuotaDetail> details = new ArrayList<QuotaDetail>();
}


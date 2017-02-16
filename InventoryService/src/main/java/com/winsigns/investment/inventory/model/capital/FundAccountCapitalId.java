package com.winsigns.investment.inventory.model.capital;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable  
public class FundAccountCapitalId implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "fund_account_id", length = 32)
	private String fundAccountId;
	
	@Column(name = "ext_capital_account_type", length = 20)
	private String extCapitalAccountType;
	
	@Column(name = "currency", length = 20)
	private String currency;
	
	public String getFundAccountId(){
		return this.fundAccountId;
	}
	
	public void setFundAccountId(String fundAccountId){
		this.fundAccountId = fundAccountId;
	}
	
	public String getExtCapitalAccountType(){
		return this.extCapitalAccountType;
	}
	
	public void setExtCapitalAccountType(String extCapitalAccountType){
		this.extCapitalAccountType = extCapitalAccountType;
	}
	
	public String getCurrency(){
		return this.currency;
	}
	
	public void setCurrency(String currency){
		this.currency = currency;
	}
}

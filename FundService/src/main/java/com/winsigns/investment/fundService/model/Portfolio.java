package com.winsigns.investment.fundService.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Created by colin on 2017/2/6.
 */
@Entity
public class Portfolio extends AbstractEntity{
	//组合名称
	private String name;
	
	//创建日期
	@Temporal(TemporalType.DATE)
	private Date createDate;
	
    //产品账户
    @ManyToOne
    private FundAccount fundAccount;
    
    public Portfolio setName(String name){
    	this.name = name;
    	return this;
    }
    
    public String getName(){
    	return this.name;
    }
    
    public Portfolio setCreateDate(Date createDate){
    	this.createDate = createDate;
    	return this;
    }
    
    public Date getCreateDate(){
    	return this.createDate;
    }
    
    public Portfolio setFundAccount(FundAccount fundAccount){
    	this.fundAccount = fundAccount;
    	return this;
    }
    
    public FundAccount getFundAccount(){
    	return this.fundAccount;
    }
}

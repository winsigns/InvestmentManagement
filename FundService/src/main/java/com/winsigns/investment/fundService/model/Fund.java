package com.winsigns.investment.fundService.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import java.lang.String;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by colin on 2017/2/6.
 */

@Entity
public class Fund extends AbstractEntity{
	
	//编码
    private String code;
    
    //名称
    private String name;

    //简称
    private String shortName;
    
    //运作方式
    
    //份额
    private Long fundUnit;
    
    //生命周期阶段
    
    @OneToMany(mappedBy = "fund", cascade=CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<FundAccount> accounts = new HashSet<FundAccount>();
    
    public String getCode() {
        return code;
    }

    public Fund setCode(String code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public Fund setName(String name) {
        this.name = name;
        return this;
    }

    public String getShortName() {
        return shortName;
    }

    public Fund setShortName(String shortName) {
        this.shortName = shortName;
        return this;
    }
    
    public Long getFundUnit() {
        return fundUnit;
    }

    public Fund setFundUnit(Long fundUnit) {
        this.fundUnit = fundUnit;
        return this;
    }

    public Set<FundAccount> getFundAccounts() {
        return accounts;
    }

    public Fund setFundAccounts(Set<FundAccount> accounts) {
        this.accounts = accounts;
        return this;
    }
}

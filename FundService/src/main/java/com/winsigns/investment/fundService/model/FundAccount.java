package com.winsigns.investment.fundService.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * Created by colin on 2017/2/6.
 */

@Entity
public class FundAccount extends AbstractEntity{
    //名称
    private String name;
    
    //投资经理
    private String investmentManager;
    
    //临时投资经理
    
    //投资组合
    @OneToMany(mappedBy = "fundAccount", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Portfolio> portfolios = new HashSet<Portfolio>();
    
    //基金
    @ManyToOne
    private Fund fund;
    
    public String getName() {
        return name;
    }

    public FundAccount setName(String name) {
        this.name = name;
        return this;
    }
    
    public String getInvestmentManager() {
        return investmentManager;
    }

    public FundAccount setInvestmentManager(String investmentManager) {
        this.investmentManager = investmentManager;
        return this;
    }
    
    public Set<Portfolio> getPortfolios() {
        return portfolios;
    }

    public FundAccount setPortfolios(Set<Portfolio> portfolios) {
        this.portfolios = portfolios;
        return this;
    }

    public Fund getFund() {
        return fund;
    }

    public FundAccount setFund(Fund fund) {
        this.fund = fund;
        return this;
    }    
}

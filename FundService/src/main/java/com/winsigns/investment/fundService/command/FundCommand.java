package com.winsigns.investment.fundService.command;

import java.lang.String;

public class FundCommand{
	
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
    
    public String getCode() {
        return code;
    }

    public FundCommand setCode(String code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public FundCommand setName(String name) {
        this.name = name;
        return this;
    }

    public String getShortName() {
        return shortName;
    }

    public FundCommand setShortName(String shortName) {
        this.shortName = shortName;
        return this;
    }
    
    public Long getFundUnit() {
        return fundUnit;
    }

    public FundCommand setFundUnit(Long fundUnit) {
        this.fundUnit = fundUnit;
        return this;
    }

}

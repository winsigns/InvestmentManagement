package com.winsigns.investment.fundService.command;

import com.winsigns.investment.fundService.constant.ExternalTradeAccountType;

public class CreateExternalTradeAccountCommand {

    private Long fundId;

    private Long externalCapitalAccountId;

    private ExternalTradeAccountType externalTradeAccountType;

    private String accountNo;

    public Long getFundId() {
        return fundId;
    }

    public void setFundId(Long fundId) {
        this.fundId = fundId;
    }

    public Long getExternalCapitalAccountId() {
        return externalCapitalAccountId;
    }

    public void setExternalCapitalAccountId(Long externalCapitalAccountId) {
        this.externalCapitalAccountId = externalCapitalAccountId;
    }

    public ExternalTradeAccountType getExternalTradeAccountType() {
        return externalTradeAccountType;
    }

    public void setExternalTradeAccountType(ExternalTradeAccountType externalTradeAccountType) {
        this.externalTradeAccountType = externalTradeAccountType;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String externalTradeAccount) {
        this.accountNo = externalTradeAccount;
    }

}

package com.winsigns.investment.inventoryService.model;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.winsigns.investment.inventoryService.framework.AbstractEntity;

@Entity
@Relation(value = "fa-capital", collectionRelation = "fa-capitals")
public class FundAccountCapital extends AbstractEntity {

    private Long fundAccountId;

    private String externalCapitalAccountType;

    private Currency currency;

    private Double investmentLimit;

    @OneToMany(mappedBy = "fundAccountCapital", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<FundAccountCapitalDetail> fundAccountCapitalDetails = new ArrayList<FundAccountCapitalDetail>();

    public Long getFundAccountId() {
        return fundAccountId;
    }

    public void setFundAccountId(Long fundAccountId) {
        this.fundAccountId = fundAccountId;
    }

    public String getExternalCapitalAccountType() {
        return externalCapitalAccountType;
    }

    public void setExternalCapitalAccountType(String externalCapitalAccountType) {
        this.externalCapitalAccountType = externalCapitalAccountType;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Double getInvestmentLimit() {
        return investmentLimit;
    }

    public void setInvestmentLimit(Double investmentLimit) {
        this.investmentLimit = investmentLimit;
    }

    public List<FundAccountCapitalDetail> getFundAccountCapitalDetails() {
        return fundAccountCapitalDetails;
    }

    public void setFundAccountCapitalDetails(List<FundAccountCapitalDetail> fundAccountCapitalDetails) {
        this.fundAccountCapitalDetails = fundAccountCapitalDetails;
    }

}

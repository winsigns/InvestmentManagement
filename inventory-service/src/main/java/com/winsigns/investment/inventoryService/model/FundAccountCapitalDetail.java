package com.winsigns.investment.inventoryService.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.winsigns.investment.inventoryService.framework.AbstractEntity;

@Entity
@Relation(value = "fa-capital-detail", collectionRelation = "fa-capital-details")
public class FundAccountCapitalDetail extends AbstractEntity {

    @ManyToOne
    @JsonIgnore
    private FundAccountCapital fundAccountCapital;

    private Long externalCapitalAccountId;

    private Double cash;

    public FundAccountCapital getFundAccountCapital() {
        return fundAccountCapital;
    }

    public void setFundAccountCapital(FundAccountCapital fundAccountCapital) {
        this.fundAccountCapital = fundAccountCapital;
    }

    public Long getExternalCapitalAccountId() {
        return externalCapitalAccountId;
    }

    public void setExternalCapitalAccountId(Long externalCapitalAccountId) {
        this.externalCapitalAccountId = externalCapitalAccountId;
    }

    public Double getCash() {
        return cash;
    }

    public void setCash(Double cash) {
        this.cash = cash;
    }

}

package com.winsigns.investment.inventoryService.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.winsigns.investment.inventoryService.framework.AbstractEntity;

@Entity
public class FundAccountCapital extends AbstractEntity {

	private Long fundAccountId;

	private Long externalCapitalAccountTypeId;

	private Long currencyId;

	private Double investmentLimit;

	@OneToMany(mappedBy = "fundAccountCapital", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<FundAccountCapitalDetail> fundAccountCapitalDetails = new ArrayList<FundAccountCapitalDetail>();

	public Long getFundAccountId() {
		return fundAccountId;
	}

	public void setFundAccountId(Long fundAccountId) {
		this.fundAccountId = fundAccountId;
	}

	public Long getExternalCapitalAccountTypeId() {
		return externalCapitalAccountTypeId;
	}

	public void setExternalCapitalAccountTypeId(Long externalCapitalAccountTypeId) {
		this.externalCapitalAccountTypeId = externalCapitalAccountTypeId;
	}

	public Long getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
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

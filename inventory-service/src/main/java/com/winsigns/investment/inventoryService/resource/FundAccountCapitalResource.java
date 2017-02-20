package com.winsigns.investment.inventoryService.resource;

import java.util.List;

import org.springframework.hateoas.Resource;

import com.winsigns.investment.inventoryService.model.FundAccountCapital;
import com.winsigns.investment.inventoryService.model.FundAccountCapitalDetail;

public class FundAccountCapitalResource extends Resource<FundAccountCapital> {

	private final Long fundAccountId;

	private final Long externalCapitalAccountTypeId;

	private final Long currencyId;

	private final Double investmentLimit;

	private final List<FundAccountCapitalDetail> fundAccountCapitalDetails;

	public FundAccountCapitalResource(FundAccountCapital fundAccountCapital) {
		super(fundAccountCapital);
		this.fundAccountId = fundAccountCapital.getFundAccountId();
		this.externalCapitalAccountTypeId = fundAccountCapital.getExternalCapitalAccountTypeId();
		this.currencyId = fundAccountCapital.getCurrencyId();
		this.investmentLimit = fundAccountCapital.getInvestmentLimit();
		this.fundAccountCapitalDetails = fundAccountCapital.getFundAccountCapitalDetails();
	}

	public Long getFundAccountId() {
		return fundAccountId;
	}

	public Long getExternalCapitalAccountTypeId() {
		return externalCapitalAccountTypeId;
	}

	public Long getCurrencyId() {
		return currencyId;
	}

	public Double getInvestmentLimit() {
		return investmentLimit;
	}

	public List<FundAccountCapitalDetail> getFundAccountCapitalDetails() {
		return fundAccountCapitalDetails;
	}

}

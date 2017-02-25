package com.winsigns.investment.inventoryService.resource;

import org.springframework.hateoas.Resource;

import com.winsigns.investment.inventoryService.model.ExternalCapitalAccountCapital;

public class ExternalCapitalAccountCapitalResource extends Resource<ExternalCapitalAccountCapital> {

	private final Long externalCapitalAccountId;

	private final Long currencyId;

	private final Double unassignedCapital;

	public ExternalCapitalAccountCapitalResource(ExternalCapitalAccountCapital externalCapitalAccountCapital) {
		super(externalCapitalAccountCapital);
		this.externalCapitalAccountId = externalCapitalAccountCapital.getExternalCapitalAccountId();
		this.currencyId = externalCapitalAccountCapital.getCurrencyId();
		this.unassignedCapital = externalCapitalAccountCapital.getUnassignedCapital();
	}

	public Long getExternalCapitalAccountId() {
		return externalCapitalAccountId;
	}

	public Long getCurrencyId() {
		return currencyId;
	}

	public Double getUnassignedCapital() {
		return unassignedCapital;
	}

}

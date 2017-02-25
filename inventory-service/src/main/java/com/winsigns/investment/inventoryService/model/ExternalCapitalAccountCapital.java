/**
 * 
 */
package com.winsigns.investment.inventoryService.model;

import javax.persistence.Entity;

import com.winsigns.investment.frame.model.AbstractEntity;

@Entity
public class ExternalCapitalAccountCapital extends AbstractEntity {

	private Long externalCapitalAccountId;

	private Long currencyId;

	private Double unassignedCapital;

	public Long getExternalCapitalAccountId() {
		return externalCapitalAccountId;
	}

	public void setExternalCapitalAccountId(Long externalCapitalAccountId) {
		this.externalCapitalAccountId = externalCapitalAccountId;
	}

	public Long getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}

	public Double getUnassignedCapital() {
		return unassignedCapital;
	}

	public void setUnassignedCapital(Double capital) {
		this.unassignedCapital = capital;
	}

}

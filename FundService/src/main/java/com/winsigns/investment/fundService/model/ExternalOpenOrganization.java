package com.winsigns.investment.fundService.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import com.winsigns.investment.frame.model.AbstractEntity;

@Entity
public class ExternalOpenOrganization extends AbstractEntity {

	// 机构名称
	private String name;

	// 简称
	private String shortName;

	// 机构类型
	@OneToOne
	private ExternalOpenOrganizationType externalOpenOrganizationType;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public ExternalOpenOrganizationType getExternalOpenOrganizationType() {
		return externalOpenOrganizationType;
	}

	public void setExternalOpenOrganizationType(ExternalOpenOrganizationType externalOpenOrganizationType) {
		this.externalOpenOrganizationType = externalOpenOrganizationType;
	}

}

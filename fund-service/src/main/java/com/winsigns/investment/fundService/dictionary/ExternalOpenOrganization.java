package com.winsigns.investment.fundService.dictionary;

import javax.persistence.Entity;

@Entity
public class ExternalOpenOrganization {

	// 机构名称
	private String name;

	// 简称
	private String shortName;

	// 机构类型
	private Long externalOpenOrganizationTypeId;

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

	public Long getExternalOpenOrganizationTypeId() {
		return externalOpenOrganizationTypeId;
	}

	public void setExternalOpenOrganizationTypeId(Long externalOpenOrganizationTypeId) {
		this.externalOpenOrganizationTypeId = externalOpenOrganizationTypeId;
	}

}

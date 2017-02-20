package com.winsigns.investment.fundService.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.winsigns.investment.frame.model.AbstractEntity;

@Entity
public class ExternalOpenOrganizationType extends AbstractEntity {

	// 机构类型名称
	private String name;

	@ManyToMany(mappedBy = "supportExternalOpenOrganizationTypes")
	@JsonIgnore
	private List<ExternalCapitalAccountType> usedExternalCapitalAccountTypes = new ArrayList<ExternalCapitalAccountType>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ExternalCapitalAccountType> getUsedExternalCapitalAccountTypes() {
		return usedExternalCapitalAccountTypes;
	}

	public void setUsedExternalCapitalAccountTypes(List<ExternalCapitalAccountType> usedExternalCapitalAccountTypes) {
		this.usedExternalCapitalAccountTypes = usedExternalCapitalAccountTypes;
	}

}

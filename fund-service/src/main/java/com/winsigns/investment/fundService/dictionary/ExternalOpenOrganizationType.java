package com.winsigns.investment.fundService.dictionary;

import java.util.ArrayList;
import java.util.List;

public class ExternalOpenOrganizationType {

	private String name;

	private List<Long> externalCapitalAccountTypes = new ArrayList<Long>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Long> getExternalCapitalAccountTypes() {
		return externalCapitalAccountTypes;
	}

	public void setExternalCapitalAccountTypes(List<Long> externalCapitalAccountTypes) {
		this.externalCapitalAccountTypes = externalCapitalAccountTypes;
	}

}

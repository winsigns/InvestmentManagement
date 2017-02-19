package com.winsigns.investment.inventoryService.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import com.winsigns.investment.frame.model.DictionaryAbstractEntity;

@Entity
public class Currency extends DictionaryAbstractEntity {

	private String name;

	private String chineseName;

	@ManyToMany(mappedBy = "supportCurrencies")
	private List<ExternalCapitalAccountType> usedExternalCapitalAccountTypes = new ArrayList<ExternalCapitalAccountType>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public List<ExternalCapitalAccountType> getUsedExternalCapitalAccountTypes() {
		return usedExternalCapitalAccountTypes;
	}

	public void setUsedExternalCapitalAccountTypes(List<ExternalCapitalAccountType> usedExternalCapitalAccountTypes) {
		this.usedExternalCapitalAccountTypes = usedExternalCapitalAccountTypes;
	}

}

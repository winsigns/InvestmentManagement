package com.winsigns.investment.inventoryService.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.winsigns.investment.frame.model.AbstractEntity;

@Entity
public class ExternalCapitalAccountType extends AbstractEntity {
	// 名称
	private String name;

	// 支持的币种
	@JoinTable(name = "external_capital_account_type_to_currency", joinColumns = {
			@JoinColumn(name = "external_capital_account_type_id") }, inverseJoinColumns = {
					@JoinColumn(name = "currency_id") })
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Currency> supportCurrencies = new ArrayList<Currency>();

	// 支持的外部开户机构类型
	@JoinTable(name = "external_capital_account_type_to_external_open_organization_type", joinColumns = {
			@JoinColumn(name = "external_capital_account_type_id") }, inverseJoinColumns = {
					@JoinColumn(name = "external_open_organization_type_id") })
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ExternalOpenOrganizationType> supportExternalOpenOrganizationTypes = new ArrayList<ExternalOpenOrganizationType>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Currency> getSupportCurrencies() {
		return supportCurrencies;
	}

	public void setSupportCurrencies(List<Currency> supportCurrencies) {
		this.supportCurrencies = supportCurrencies;
	}

	public List<ExternalOpenOrganizationType> getSupportExternalOpenOrganizations() {
		return supportExternalOpenOrganizationTypes;
	}

	public void setSupportExternalOpenOrganizations(
			List<ExternalOpenOrganizationType> supportExternalOpenOrganizationTypes) {
		this.supportExternalOpenOrganizationTypes = supportExternalOpenOrganizationTypes;
	}

}

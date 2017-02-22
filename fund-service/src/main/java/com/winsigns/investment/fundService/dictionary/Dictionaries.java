package com.winsigns.investment.fundService.dictionary;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties()
@PropertySource(value = "classpath:dictionaries.properties", encoding = "UTF-8")
public class Dictionaries {

	private List<ExternalOpenOrganizationType> externalOpenOrganizationTypes = new ArrayList<ExternalOpenOrganizationType>();

	private List<ExternalCapitalAccountType> externalCapitalAccountTypes = new ArrayList<ExternalCapitalAccountType>();

	private List<ExternalTradeAccountType> externalTradeAccountTypes = new ArrayList<ExternalTradeAccountType>();

	private List<ExternalOpenOrganization> externalOpenOrganizations = new ArrayList<ExternalOpenOrganization>();

	public List<ExternalOpenOrganizationType> getExternalOpenOrganizationTypes() {
		return externalOpenOrganizationTypes;
	}

	public void setExternalOpenOrganizationTypes(List<ExternalOpenOrganizationType> externalOpenOrganizationTypes) {
		this.externalOpenOrganizationTypes = externalOpenOrganizationTypes;
	}

	public List<ExternalCapitalAccountType> getExternalCapitalAccountTypes() {
		return externalCapitalAccountTypes;
	}

	public void setExternalCapitalAccountTypes(List<ExternalCapitalAccountType> externalCapitalAccountTypes) {
		this.externalCapitalAccountTypes = externalCapitalAccountTypes;
	}

	public List<ExternalTradeAccountType> getExternalTradeAccountTypes() {
		return externalTradeAccountTypes;
	}

	public void setExternalTradeAccountTypes(List<ExternalTradeAccountType> externalTradeAccountTypes) {
		this.externalTradeAccountTypes = externalTradeAccountTypes;
	}

	public List<ExternalOpenOrganization> getExternalOpenOrganizations() {
		return externalOpenOrganizations;
	}

	public void setExternalOpenOrganizations(List<ExternalOpenOrganization> externalOpenOrganizations) {
		this.externalOpenOrganizations = externalOpenOrganizations;
	}

}

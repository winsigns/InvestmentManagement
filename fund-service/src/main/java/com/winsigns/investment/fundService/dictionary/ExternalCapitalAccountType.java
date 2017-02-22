package com.winsigns.investment.fundService.dictionary;

import java.util.ArrayList;
import java.util.List;

public class ExternalCapitalAccountType {

	private String name;

	private List<String> currencies = new ArrayList<String>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getCurrencies() {
		return currencies;
	}

	public void setCurrencies(List<String> currencies) {
		this.currencies = currencies;
	}

}

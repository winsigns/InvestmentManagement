package com.winsigns.investment.fundService.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * Created by colin on 2017/2/6.
 */

@Entity
public class FundAccount extends AbstractEntity {
	// 名称
	private String name;

	// 投资组合
	@OneToMany(mappedBy = "fundAccount", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Portfolio> portfolios = new HashSet<Portfolio>();

	// 基金
	@ManyToOne
	private Fund fund;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Portfolio> getPortfolios() {
		return portfolios;
	}

	public void setPortfolios(Set<Portfolio> portfolios) {
		this.portfolios = portfolios;
	}

	public Fund getFund() {
		return fund;
	}

	public void setFund(Fund fund) {
		this.fund = fund;
	}

}

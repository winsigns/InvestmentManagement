package com.winsigns.investment.fundService.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.winsigns.investment.fundService.framework.AbstractEntity;

/**
 * Created by colin on 2017/2/6.
 */

@Entity
@Relation(value = "fundAccount", collectionRelation = "fundAccounts")
public class FundAccount extends AbstractEntity {
	// 名称
	private String name;

	// 投资组合
	@OneToMany(mappedBy = "fundAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Portfolio> portfolios = new ArrayList<Portfolio>();

	// 基金
	@ManyToOne
	@JsonIgnore
	private Fund fund;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Portfolio> getPortfolios() {
		return portfolios;
	}

	public void setPortfolios(List<Portfolio> portfolios) {
		this.portfolios = portfolios;
	}

	public Fund getFund() {
		return fund;
	}

	public void setFund(Fund fund) {
		this.fund = fund;
	}

}

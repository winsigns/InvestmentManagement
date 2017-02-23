package com.winsigns.investment.fundService.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.winsigns.investment.fundService.framework.AbstractEntity;

/**
 * Created by colin on 2017/2/6.
 */

@Entity
@Relation(value = "fund", collectionRelation = "funds")
public class Fund extends AbstractEntity {

	// 编码
	private String code;

	// 名称
	private String name;

	// 简称
	private String shortName;

	// 份额
	private Long totalShares;

	@OneToMany(mappedBy = "fund", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<FundAccount> fundAccounts = new ArrayList<FundAccount>();

	@OneToMany(mappedBy = "fund", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<ExternalCapitalAccount> externalCapitalAccounts = new ArrayList<ExternalCapitalAccount>();

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

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

	public Long getTotalShares() {
		return totalShares;
	}

	public void setTotalShares(Long totalShares) {
		this.totalShares = totalShares;
	}

	public List<FundAccount> getFundAccounts() {
		return fundAccounts;
	}

	public void setFundAccounts(List<FundAccount> fundAccounts) {
		this.fundAccounts = fundAccounts;
	}

	public List<ExternalCapitalAccount> getExternalCapitalAccounts() {
		return externalCapitalAccounts;
	}

	public void setExternalCapitalAccounts(List<ExternalCapitalAccount> externalCapitalAccounts) {
		this.externalCapitalAccounts = externalCapitalAccounts;
	}

}

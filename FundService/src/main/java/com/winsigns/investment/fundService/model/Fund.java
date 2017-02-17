package com.winsigns.investment.fundService.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

/**
 * Created by colin on 2017/2/6.
 */

@Entity
public class Fund extends AbstractEntity {

	// 编码
	private String code;

	// 名称
	private String name;

	// 简称
	private String shortName;

	// 份额
	private Long totalShares;

	@OneToMany(mappedBy = "fund", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<FundAccount> accounts = new HashSet<FundAccount>();

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

	public Set<FundAccount> getAccounts() {
		return accounts;
	}

	public void setAccounts(Set<FundAccount> accounts) {
		this.accounts = accounts;
	}

}

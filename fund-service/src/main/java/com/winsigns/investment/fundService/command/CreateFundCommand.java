package com.winsigns.investment.fundService.command;

public class CreateFundCommand {

	// 编码
	private String code;

	// 名称
	private String name;

	// 简称
	private String shortName;

	// 份额
	private Long totalShares;

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

}

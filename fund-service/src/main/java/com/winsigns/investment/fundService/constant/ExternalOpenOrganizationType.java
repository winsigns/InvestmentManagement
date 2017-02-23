package com.winsigns.investment.fundService.constant;

import com.winsigns.investment.fundService.framework.i18nHelper;

public enum ExternalOpenOrganizationType {

	// 核心机构
	CORE_ORGANIZATION,

	// 证券公司
	SECURITIES_COMPANY,

	// 期货公司
	FUTURES_COMPANY,

	// 基金公司
	FUND_COMPANY;

	public String i18n() {
		return i18nHelper.i18n(this);
	}
}

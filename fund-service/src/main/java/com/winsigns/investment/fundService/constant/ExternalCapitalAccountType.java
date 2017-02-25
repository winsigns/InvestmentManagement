package com.winsigns.investment.fundService.constant;

import com.winsigns.investment.fundService.framework.CurrencyCode;
import com.winsigns.investment.fundService.framework.i18nHelper;

import java.util.*;

import static java.util.Arrays.asList;

public enum ExternalCapitalAccountType {

	// 普通资金账户
	CHINA_GENERAL_CAPITAL_ACCOUNT,

	// 信用资金账户

	// 衍生品保证金账户

	// 期货保证金账户
	CHINA_FUTURE_CAPITAL_ACCOUNT;

	// 中债登结算资金专户

	// 上海清算所结算资金专户

	// 黄金交易账户

	// 外汇存款账户

	// 香港专用信托户口

	// 美国证券经纪账户

	// 自定义标的账户

    // 支持的币种
	private static HashMap<ExternalCapitalAccountType, List<Currency>> supportCurrencies
            = new HashMap<ExternalCapitalAccountType,  List<Currency>>();

    static {
        supportCurrencies.put(CHINA_GENERAL_CAPITAL_ACCOUNT, asList(
                Currency.getInstance(CurrencyCode.CNY),
                Currency.getInstance(CurrencyCode.HKD),
                Currency.getInstance(CurrencyCode.USD) ));

        supportCurrencies.put(CHINA_FUTURE_CAPITAL_ACCOUNT, asList(
                Currency.getInstance(Locale.CHINA) ));
    }

	public String i18n() {
		return i18nHelper.i18n(this);
	}

	public Collection<Currency> getSupportedCurrency() {
        return supportCurrencies.get(this);
    }

    public static Collection<Currency> getSupportedCurrency(ExternalCapitalAccountType accountType) {
        return accountType.getSupportedCurrency();
    }

    // 支持外部交易账户
    private static HashMap<ExternalCapitalAccountType, List<ExternalTradeAccountType>> supportTradeAccountTypes
            = new HashMap<ExternalCapitalAccountType,  List<ExternalTradeAccountType>>();

    static {
        supportTradeAccountTypes.put(CHINA_GENERAL_CAPITAL_ACCOUNT, asList(
                ExternalTradeAccountType.SSE_A_STOCK_ACCOUNT,
                ExternalTradeAccountType.SSE_B_STOCK_ACCOUNT,
                ExternalTradeAccountType.SZSE_A_STOCK_ACCOUNT,
                ExternalTradeAccountType.SZSE_B_STOCK_ACCOUNT ));

        supportTradeAccountTypes.put(CHINA_FUTURE_CAPITAL_ACCOUNT, asList(
                ExternalTradeAccountType.SHFE_TRADING_CODE,
                ExternalTradeAccountType.CZCE_TRADING_CODE,
                ExternalTradeAccountType.DCE_TRADING_CODE,
                ExternalTradeAccountType.CFFEX_TRADING_CODE));
    }

    public Collection<ExternalTradeAccountType> getSupportTradeAccountTypes() {
        return supportTradeAccountTypes.get(this);
    }

    public static Collection<ExternalTradeAccountType> getSupportTradeAccountTypes(ExternalCapitalAccountType accountType) {
        return accountType.getSupportTradeAccountTypes();
    }

}

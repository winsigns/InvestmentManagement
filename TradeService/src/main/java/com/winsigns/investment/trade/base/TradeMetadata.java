package com.winsigns.investment.trade.base;

public class TradeMetadata {
	
	//指令序号
	public String instructId;
	
	//投资组合
	public String portfolioId;
	
	//投资服务
	public String investSvc;
	
	//投资方向
	public String investDirection;

	//投资标的
	public String securityId;

	//对手方
	//public String counterparty;

	//币种
	public String currency;
	
	//成本价
	public Double costPrice;

	//数量类型
	public InstructionType volumeType;

	//指令数量
	public Double quantity;

	//指令金额
	public Double amount;
}

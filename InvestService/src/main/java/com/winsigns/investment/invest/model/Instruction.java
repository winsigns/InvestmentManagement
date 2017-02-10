package com.winsigns.investment.invest.model;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity  
@Table(name = "tb_instruction")  
public class Instruction {
	
	//指令序号
	@Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid") 
	@Column(name = "instruct_id", nullable = false, length = 32)
	private String instructId;

	//投资组合
	@Column(name = "portfolio_id", nullable = false, length = 20)
	private String portfolioId;
	
	//投资标的
	@Column(name = "security_id", nullable = false, length = 20)
	private String securityId;
	
	//投资服务
	@Column(name = "invest_svc", nullable = false, length = 20)
	private String investSvc;
	
	//投资方向
	@Column(name = "invest_direction", nullable = false, length = 20)
	private String investDirection;
	
	//币种
	@Column(name = "currency", nullable = false, length = 20)
	private String currency;
	
	//成本价
	@Column(name = "limit_price", nullable = true)
	private boolean limitPrice;
		
	//成本价
	@Column(name = "cost_price", nullable = true, precision = 22,scale = 4)
	private Double costPrice;
	
	//数量类型
	@Enumerated(EnumType.STRING)
	@Column(name = "volume_type", nullable = false, length = 20)
	private InstructionType volumeType;
	
	//指令数量
	@Column(name = "quantity", nullable = true, precision = 22,scale = 4)
	private Double quantity;
	
	//指令金额
	@Column(name = "amount", nullable = true, precision = 22,scale = 4)
	private Double amount;
	
	//对手方
	@Column(name = "counterparty", nullable = true, length = 255)
	private String counterparty;
	
	public Instruction(){
	}
	
	public Instruction(String instructId){
		this.instructId = instructId;
	}
	
	public void setInstructId(String instructId){
		this.instructId = instructId;
	}
	
	public String getInstructId(){
		return instructId;
	}
	
	public void setPortfolioId(String portfolioId){
		this.portfolioId = portfolioId;
	}
	
	public String getPortfolioId(){
		return this.portfolioId;
	}
	
	public void setSecurityId(String securityId){
		this.securityId = securityId;
	}
	
	public String getSecurityId() {
		// TODO Auto-generated method stub
		return this.securityId;
	}
	
	public void setInvestSvc(String investSvc){
		this.investSvc = investSvc;
	}
	
	public String getInvestSvc() {
		// TODO Auto-generated method stub
		return this.investSvc;
	}
	
	public void setInvestDirection(String investDirection){
		this.investDirection = investDirection;
	}
	
	public String getInvestDirection() {
		// TODO Auto-generated method stub
		return this.investDirection;
	}
	
	public void setCurrency(String currency){
		this.currency = currency;
	}
	
	public String getCurrency() {
		// TODO Auto-generated method stub
		return this.currency;
	}
	
	public void setLimitPrice(boolean limitPrice){
		this.limitPrice = limitPrice;
	}
	
	public boolean getLimitPrice(){
		return this.limitPrice;
	}
	
	public void setCostPrice(Double costPrice){
		this.costPrice = costPrice;
	}
	
	public double getCostPrice() {
		// TODO Auto-generated method stub
		return this.costPrice;
	}
	
	public void setVolumeType(InstructionType volumeType){
		this.volumeType = volumeType;
	}
	
	public InstructionType getVolumeType() {
		// TODO Auto-generated method stub
		return this.volumeType;
	}
	
	public void setQuantity(Double quantity){
		this.quantity = quantity;
	}
	
	public double getQuantity() {
		// TODO Auto-generated method stub
		return this.quantity;
	}
	
	public void setAmount(Double amount){
		this.amount = amount;
	}
	
	public double getAmount() {
		// TODO Auto-generated method stub
		return this.amount;
	}
	
	public void setCounterparty(String counterparty){
		this.counterparty = counterparty;
	}

	public String getCounterparty() {
		// TODO Auto-generated method stub
		return this.counterparty;
	}

	
	
}

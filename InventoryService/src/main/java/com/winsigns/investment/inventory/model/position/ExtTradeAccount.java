package com.winsigns.investment.inventory.model.position;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity  
@Table(name = "tb_ext_trade_account") 
public class ExtTradeAccount {
	
	@Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid") 
	@Column(name = "ext_trade_account_id", nullable = false, length = 32)
	private String extTradeAccountId;
	
	@Column(name = "ext_capital_account_id", nullable = false, length = 32)
	private String extCapitalAccountId;
	
	@Column(name = "ext_trade_account_type", length = 20)
	private String extCapitalAccountType;
	
	//TODO 开通的投资范围

}

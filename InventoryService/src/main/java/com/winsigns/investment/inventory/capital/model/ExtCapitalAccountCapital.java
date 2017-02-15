/**
 * 
 */
package com.winsigns.investment.inventory.capital.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author jym92
 *
 */
@Entity  
@Table(name = "tb_ext_capital_account_capital") 
public class ExtCapitalAccountCapital {

	@EmbeddedId
	private ExtCapitalAccountCapitalId extCapitalAccountId;
	
//	@Column(name = "fund_id", length = 32)
//	private String fundId;
	
//	@Column(name = "ext_capital_account_type", length = 20)
//	private String extCapitalAccountType;
//	
//	@Column(name = "ext_capital_account", length = 20)
//	private String extCapitalAccount;
//	
//	@Column(name = "ext_open_organization", length = 20)
//	private String extOpenOrganization;
	
	@Column(name = "unassign_capital", precision = 22,scale = 4)
	private Double unassignCapital;
	
	//TODO 是否直通
	
	public ExtCapitalAccountCapitalId getExtCapitalAccountId(){
		return this.extCapitalAccountId;
	}
	
	public void setExtCapitalAccountCapitalId(ExtCapitalAccountCapitalId extCapitalAccountId){
		this.extCapitalAccountId = extCapitalAccountId;
	}
	
	public Double getUnassignCapital(){
		return this.unassignCapital;
	}
	
	public void setUnassignCapital(Double unassignCapital){
		this.unassignCapital = unassignCapital;
	}
}

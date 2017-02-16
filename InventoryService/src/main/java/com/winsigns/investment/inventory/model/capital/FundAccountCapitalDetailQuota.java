package com.winsigns.investment.inventory.model.capital;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

// [float_]cash --[浮动]现金
// [float_]avail_capital  --[浮动]可用资金
// [float_]desirable_capital --[浮动]可取资金
@Entity  
@Table(name = "tb_fund_account_capital_detail_quota")  
public class FundAccountCapitalDetailQuota {

	@Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid") 
	@Column(name = "fund_account_capital_detail_quota_id", nullable = false, length = 32)
	private String fundAccountCapitalDetailQuota;

	@Column(name = "name", nullable = false, length = 64)
    private String name;
    
	@Column(name = "value", nullable = false, precision = 22,scale = 6)
    private Double value;
    
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setValue(Double value){
		this.value = value;
	}
	
	public Double getValue(){
		return this.value;
	}
	
	public FundAccountCapitalDetail getQuota() {
	        return this.fundAccountCapitalDetail;
	}
	
    public void setQuota(FundAccountCapitalDetail fundAccountCapitalDetail) {
        this.fundAccountCapitalDetail = fundAccountCapitalDetail;
    }

	@ManyToOne
    //@JoinColumn(name="fundAccountId")
    private FundAccountCapitalDetail fundAccountCapitalDetail;
}

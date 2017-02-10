package com.winsigns.investment.trade.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity  
@Table(name = "tb_quota_detail")  
public class QuotaDetail {

	@Id
    @GeneratedValue
	private Long id;

	//@Column(name = "name", nullable = false, length = 20)
    private String name;
    
	//@Column(name = "value", nullable = false, precision = 22,scale = 6)
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
	
	public Quota getQuota() {
	        return this.quota;
	}
	
    public void setQuota(Quota quota) {
        this.quota = quota;
    }

	@ManyToOne
    @JoinColumn(name="quotaId")
    private Quota quota;
}

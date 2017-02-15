package com.winsigns.investment.inventory.capital.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity 
//@IdClass(FundAccountCapitalDetailId.class)
@Table(name = "tb_fund_account_capital_detail") 
public class FundAccountCapitalDetail {
	
	@EmbeddedId 
	private FundAccountCapitalDetailId fundAccountCapitalDetailId;

    @OneToMany(mappedBy = "fundAccountCapitalDetail", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    private List<FundAccountCapitalDetailQuota> quotas = new ArrayList<FundAccountCapitalDetailQuota>();
    
    public FundAccountCapitalDetailId getFundAccountCapitalDetailId(){
		return this.fundAccountCapitalDetailId;
	}
	
	public void setFundAccountCapitalDetailId(FundAccountCapitalDetailId fundAccountCapitalDetailId){
		this.fundAccountCapitalDetailId = fundAccountCapitalDetailId;
	}
	
	public List<FundAccountCapitalDetailQuota> getQuotas() {
        return quotas;
    }

    public void setQuotas(List<FundAccountCapitalDetailQuota> quotas) {
        this.quotas = quotas;
    }
}

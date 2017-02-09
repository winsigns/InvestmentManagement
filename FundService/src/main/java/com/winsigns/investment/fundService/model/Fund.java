package com.winsigns.investment.fundService.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.lang.String;
import java.util.List;

/**
 * Created by colin on 2017/2/6.
 */

@Entity
public class Fund {


	public Long getId(){
		return id;
	}
	
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

    @Id
    @GeneratedValue
    private Long id;

    private String code;

    private String name;

    private String shortName;

    public List<FundAccount> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<FundAccount> accounts) {
        this.accounts = accounts;
    }

    @OneToMany(mappedBy = "fund")
    private List<FundAccount> accounts;
}

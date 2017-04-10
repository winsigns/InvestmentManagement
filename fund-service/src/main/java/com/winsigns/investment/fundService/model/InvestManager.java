package com.winsigns.investment.fundService.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.winsigns.investment.framework.model.AbstractEntity;

/**
 * 投资经理
 * 
 * @author yimingjin
 *
 */
@Entity
public class InvestManager extends AbstractEntity {

  @OneToMany(mappedBy = "investManager", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonIgnore
  private List<FundAccount> fundAccounts = new ArrayList<FundAccount>();

}

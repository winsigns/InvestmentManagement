package com.winsigns.investment.fundService.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.winsigns.investment.framework.model.AbstractEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by colin on 2017/2/6.
 */

@Entity
@Relation(value = "fund-account", collectionRelation = "fund-accounts")
public class FundAccount extends AbstractEntity {

  // 名称
  @Getter
  @Setter
  private String name;

  // 投资组合
  @OneToMany(mappedBy = "fundAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonIgnore
  @Getter
  @Setter
  private List<Portfolio> portfolios = new ArrayList<Portfolio>();

  // 基金
  @ManyToOne
  @JsonIgnore
  @Getter
  @Setter
  private Fund fund;

  @Getter
  @Setter
  @ManyToOne
  @JsonIgnore
  private InvestManager investManager;
}

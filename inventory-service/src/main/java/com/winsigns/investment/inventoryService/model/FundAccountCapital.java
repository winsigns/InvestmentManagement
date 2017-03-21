package com.winsigns.investment.inventoryService.model;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.winsigns.investment.framework.model.AbstractEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Relation(value = "fa-capital", collectionRelation = "fa-capitals")
public class FundAccountCapital extends AbstractEntity {

  /*
   * 产品账户序号
   */
  @Getter
  @Setter
  private Long fundAccountId;

  /*
   * 外部资金账户类型
   */
  @Getter
  @Setter
  private String externalCapitalAccountType;

  /*
   * 币种
   */
  @Getter
  @Setter
  private Currency currency;

  /*
   * 投资限额
   */
  @Getter
  @Setter
  private Double investmentLimit;

  @OneToMany(mappedBy = "fundAccountCapital", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonIgnore
  @Getter
  @Setter
  private List<FundAccountCapitalDetail> fundAccountCapitalDetails =
      new ArrayList<FundAccountCapitalDetail>();

}

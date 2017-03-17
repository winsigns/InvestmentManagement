package com.winsigns.investment.fundService.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.winsigns.investment.framework.model.AbstractEntity;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.hateoas.core.Relation;

/**
 * Created by colin on 2017/2/6.
 */
@Entity
@Relation(value = "portfolio", collectionRelation = "portfolios")
public class Portfolio extends AbstractEntity {

  // 组合名称
  private String name;

  // 创建日期
  @Temporal(TemporalType.DATE)
  private Date createDate;

  // 产品账户
  @ManyToOne
  @JsonIgnore
  private FundAccount fundAccount;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public FundAccount getFundAccount() {
    return fundAccount;
  }

  public void setFundAccount(FundAccount fundAccount) {
    this.fundAccount = fundAccount;
  }

}

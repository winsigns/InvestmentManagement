package com.winsigns.investment.webgateway.model;

import org.springframework.hateoas.core.Relation;

import com.winsigns.investment.webgateway.framework.AbstractEntity;

@Relation(value = "fund", collectionRelation = "funds")
public class Fund extends AbstractEntity {

  // 编码
  private String code;

  // 名称
  private String name;

  // 简称
  private String shortName;

  // 份额
  private Long totalShares;

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

  public Long getTotalShares() {
    return totalShares;
  }

  public void setTotalShares(Long totalShares) {
    this.totalShares = totalShares;
  }
}

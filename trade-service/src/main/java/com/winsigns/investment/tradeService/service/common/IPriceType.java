package com.winsigns.investment.tradeService.service.common;

import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 价格类型的接口
 * 
 * @author yimingjin
 *
 */
@Embeddable
public interface IPriceType {
  /**
   * 该类型的名字
   * 
   * @return
   */
  public String name();

  /**
   * 支持国际化
   * 
   * @return
   */
  @JsonIgnore
  public String i18n();
}

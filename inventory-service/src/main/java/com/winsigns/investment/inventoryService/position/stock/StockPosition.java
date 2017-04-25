package com.winsigns.investment.inventoryService.position.stock;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.springframework.hateoas.core.Relation;

import com.winsigns.investment.inventoryService.model.Position;

import lombok.Getter;

@Entity
@Relation(value = "position", collectionRelation = "positions")
@DiscriminatorValue("stock-position")
public class StockPosition extends Position {

  // 浮动持仓量
  @Getter
  protected Long floatPositionQuantity = 0L;

  // 浮动可卖持仓量
  @Getter
  protected Long floatCanSellPositionQuantity = 0L;

  // 浮动权益持仓量
  @Getter
  protected Long floatEquityPositionQuantity = 0L;

  // 持仓量
  @Getter
  protected Long positionQuantity = 0L;

  // 可卖持仓量
  @Getter
  protected Long canSellPositionQuantity = 0L;

  // 权益持仓量
  @Getter
  protected Long equityPositionQuantity = 0L;

  /**
   * 修改浮动持仓量
   * 
   * @param positionQuantity
   * @return
   */
  public Long changeFloatPositionQuantity(Long floatPositionQuantity) {
    this.floatPositionQuantity += floatPositionQuantity;
    return this.floatPositionQuantity;
  }

  /**
   * 修改浮动可卖持仓量
   * 
   * @param positionQuantity
   * @return
   */
  public Long changeFloatCanSellPositionQuantity(Long floatCanSellPositionQuantity) {
    this.floatCanSellPositionQuantity += floatCanSellPositionQuantity;
    return this.floatCanSellPositionQuantity;
  }

  /**
   * 浮动权益持仓量
   * 
   * @param positionQuantity
   * @return
   */
  public Long changeFloatEquityPositionQuantity(Long floatEquityPositionQuantity) {
    this.floatEquityPositionQuantity += floatEquityPositionQuantity;
    return this.floatEquityPositionQuantity;
  }

  /**
   * 修改持仓量
   * 
   * @param positionQuantity
   * @return
   */
  public Long changePositionQuantity(Long positionQuantity) {
    this.positionQuantity += positionQuantity;
    return this.positionQuantity;
  }

  /**
   * 修改可卖持仓量
   * 
   * @param positionQuantity
   * @return
   */
  public Long changeCanSellPositionQuantity(Long canSellPositionQuantity) {
    this.positionQuantity += positionQuantity;
    return this.positionQuantity;
  }

  /**
   * 修改权益持仓量
   * 
   * @param positionQuantity
   * @return
   */
  public Long changeEquityPositionQuantity(Long equityPositionQuantity) {
    this.equityPositionQuantity += equityPositionQuantity;
    return this.equityPositionQuantity;
  }

}

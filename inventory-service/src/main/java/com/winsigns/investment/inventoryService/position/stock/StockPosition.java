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
   * 修改持仓量
   * 
   * @param positionQuantity
   * @return
   */
  public Long changeCanSellPositionQuantity(Long canSellPositionQuantity) {
    this.positionQuantity += positionQuantity;
    return this.positionQuantity;
  }

  /**
   * 修改持仓量
   * 
   * @param positionQuantity
   * @return
   */
  public Long changeEquityPositionQuantity(Long equityPositionQuantity) {
    this.equityPositionQuantity += equityPositionQuantity;
    return this.equityPositionQuantity;
  }

}

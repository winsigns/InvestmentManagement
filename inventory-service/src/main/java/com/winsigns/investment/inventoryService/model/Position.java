package com.winsigns.investment.inventoryService.model;

import javax.persistence.Entity;

import org.springframework.hateoas.core.Relation;

import com.winsigns.investment.framework.model.AbstractEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Relation(value = "position", collectionRelation = "positions")
public class Position extends AbstractEntity {

  @Getter
  @Setter
  private Long portfolioId;

  @Getter
  @Setter
  private Long externalTradeAccountId;

  @Getter
  @Setter
  private Long securityId;

}

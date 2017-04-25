package com.winsigns.investment.inventoryService.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.springframework.hateoas.core.Relation;

import com.winsigns.investment.framework.model.AbstractEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Relation(value = "position-serial", collectionRelation = "position-serials")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
public class PositionSerial extends AbstractEntity {

  @Getter
  @Setter
  private Long portfolioId;

  @Getter
  @Setter
  private Long externalTradeAccountId;

  @Getter
  @Setter
  private Long securityId;

  @Getter
  @Setter
  @Column(name = "position_type")
  private String type;

  // 持仓量
  @Getter
  @Setter
  protected Long occurPosition = 0L;

}

package com.winsigns.investment.inventoryService.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.winsigns.investment.framework.measure.MeasureHost;
import com.winsigns.investment.framework.model.OperatorEntity;
import com.winsigns.investment.framework.spring.SpringManager;
import com.winsigns.investment.inventoryService.repository.FundAccountCapitalSerialRepository;

import lombok.Getter;
import lombok.Setter;

@Entity
@Relation(value = "fac-serial", collectionRelation = "fac-serials")
public class FundAccountCapitalSerial extends OperatorEntity {
  @ManyToOne
  @JsonIgnore
  @Getter
  @Setter
  private FundAccountCapitalDetail fundAccountCapitalDetail;

  @Getter
  @Setter
  private Double assignedCash;

  @Getter
  @Setter
  @Temporal(TemporalType.DATE)
  private Date assignedDate;

  @Getter
  @Setter
  private Long ecaCashPoolId;

  /*
   * 关联的产品账户资金流水
   */
  @Getter
  @Setter
  private Long linkedSerialId;

  @Override
  protected void doOperator(MeasureHost measureHost, boolean isFloat) {
    SpringManager.getApplicationContext().getBean(FundAccountCapitalSerialRepository.class)
        .save(this);
  }

}

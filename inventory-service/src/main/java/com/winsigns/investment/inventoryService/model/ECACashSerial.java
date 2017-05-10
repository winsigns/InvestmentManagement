package com.winsigns.investment.inventoryService.model;

import static java.util.Arrays.asList;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.winsigns.investment.framework.measure.MeasureHost;
import com.winsigns.investment.framework.model.OperatorEntity;
import com.winsigns.investment.framework.spring.SpringManager;
import com.winsigns.investment.inventoryService.repository.ECACashSerialRepository;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "eca_cash_serial")
public class ECACashSerial extends OperatorEntity {
  @ManyToOne
  @JsonIgnore
  @Getter
  @Setter
  private ECACashPool ecaCashPool;

  @Getter
  @Setter
  private Double assignedCash;

  @Getter
  @Setter
  @Temporal(TemporalType.DATE)
  private Date assignedDate;

  /*
   * 关联的外部资金账户资金流水
   */
  @Getter
  @Setter
  @Column(name = "linked_eca_serial_id")
  private Long linkedECASerialId;

  /*
   * 关联的产品账户资金流水
   */
  @Getter
  @Setter
  @Column(name = "linked_fa_serial_id")
  private Long linkedFASerialId;

  @Override
  protected List<MeasureHost> doOperator() {

    SpringManager.getApplicationContext().getBean(ECACashSerialRepository.class).save(this);

    return asList(ecaCashPool);
  }

  @Override
  public boolean isAffectedFloatMeasure() {
    return true;
  }

  @Override
  public boolean isAffectedNomalMeasure() {
    return true;
  }


}

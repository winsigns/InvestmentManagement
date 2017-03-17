package com.winsigns.investment.framework.model;

import javax.persistence.MappedSuperclass;

import com.winsigns.investment.framework.spring.SpringManager;

import lombok.Getter;

@MappedSuperclass
public abstract class OperatorEntity extends AbstractEntity {

  @Getter
  private String operatorSequence;

  public void operator() {
    operatorSequence = SpringManager.getApplicationContext()
        .getBean(OperatorSequenceIntegration.class).getSequence();

    doOperator();
  }

  protected abstract void doOperator();

}

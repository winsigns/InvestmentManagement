package com.winsigns.investment.framework.model;

import javax.persistence.MappedSuperclass;

import com.winsigns.investment.framework.measure.ICalculateFactor;
import com.winsigns.investment.framework.measure.MeasureHost;
import com.winsigns.investment.framework.measure.kafkaStreams.KafKaTrigger;
import com.winsigns.investment.framework.spring.SpringManager;

import lombok.Getter;

@MappedSuperclass
public abstract class OperatorEntity extends AbstractEntity implements ICalculateFactor {

  @Getter
  private String operatorSequence;

  public void operator(MeasureHost measureHost, boolean isFloat) {
    if (operatorSequence == null) {
      operatorSequence = SpringManager.getApplicationContext()
          .getBean(OperatorSequenceIntegration.class).getSequence();
    }

    doOperator(measureHost, isFloat);

    // 往kafka 发送异步消息
    KafKaTrigger kafKaTrigger = SpringManager.getApplicationContext().getBean(KafKaTrigger.class);

    kafKaTrigger.raiseKafka(measureHost.getId(), isFloat, operatorSequence,
        this.getClass().getSimpleName(), this.getClass().getSimpleName());

  }

  protected abstract void doOperator(MeasureHost measureHost, boolean isFloat);

  @Override
  public String getName() {
    return this.getClass().getSimpleName();
  }
}

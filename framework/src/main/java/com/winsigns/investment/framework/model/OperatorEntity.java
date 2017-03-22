package com.winsigns.investment.framework.model;

import javax.persistence.MappedSuperclass;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

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

    /* 这里需要手动开启事务 */
    PlatformTransactionManager platformTransactionManager =
        SpringManager.getApplicationContext().getBean(PlatformTransactionManager.class);
    DefaultTransactionDefinition transactionDefinition =
        new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED);
    TransactionStatus status = platformTransactionManager.getTransaction(transactionDefinition);

    try {

      doOperator(measureHost, isFloat);

      platformTransactionManager.commit(status);

      // 往kafka 发送异步消息
      KafKaTrigger kafKaTrigger = SpringManager.getApplicationContext().getBean(KafKaTrigger.class);

      kafKaTrigger.raiseKafka(measureHost.getId(), isFloat, operatorSequence,
          this.getClass().getSimpleName(), this.getClass().getSimpleName());

    } catch (Exception e) {
      // 异常回归事物
      platformTransactionManager.rollback(status);
    }

  }

  protected abstract void doOperator(MeasureHost measureHost, boolean isFloat);

  @Override
  public String getName() {
    return this.getClass().getSimpleName();
  }
}

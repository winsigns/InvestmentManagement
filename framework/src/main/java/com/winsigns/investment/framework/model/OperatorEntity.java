package com.winsigns.investment.framework.model;

import java.util.List;

import javax.persistence.MappedSuperclass;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.Assert;

import com.winsigns.investment.framework.measure.MeasureHost;
import com.winsigns.investment.framework.measure.MeasureHostType;
import com.winsigns.investment.framework.measure.MeasureRepository;
import com.winsigns.investment.framework.measure.ProcessorKey;
import com.winsigns.investment.framework.measure.ProcessorValue;
import com.winsigns.investment.framework.spring.SpringManager;

import lombok.Getter;
import lombok.extern.log4j.Log4j;

/**
 * 操作的实体类
 * <p>
 * 带有一个具体的操作，其带有自己的事务 事务成功之后，向kafka发送消息
 * 
 * @author yimingjin
 * @since 0.0.2
 *
 */
@Log4j
@MappedSuperclass
public abstract class OperatorEntity extends AbstractEntity {

  public static final String SEQUENCE_TOPIC = "operator-sequence";

  @Getter
  String operatorSequence;

  public void getSequence() {
    operatorSequence = SpringManager.getApplicationContext()
        .getBean(OperatorSequenceIntegration.class).getSequence();
  }

  public void operator() {

    boolean success = false;

    PlatformTransactionManager platformTransactionManager =
        SpringManager.getApplicationContext().getBean(PlatformTransactionManager.class);

    // PROPAGATION_REQUIRES_NEW 表示挂起当前事务，重新启动一个新事务
    DefaultTransactionDefinition transactionDefinition =
        new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
    TransactionStatus status = platformTransactionManager.getTransaction(transactionDefinition);

    List<MeasureHost> measureHosts = null;
    try {

      measureHosts = doOperator();

      platformTransactionManager.commit(status);

      success = true;

    } catch (Exception e) {
      // 异常回归事物
      log.error(e);
      platformTransactionManager.rollback(status);
    }

    if (success) {
      send(measureHosts);
    }
  }

  /**
   * 子类实现，以完成具体的业务
   * 
   * @return 返回受到影响的宿主
   */
  protected abstract List<MeasureHost> doOperator();

  /**
   * 定义是否影响浮动指标
   * 
   * @return
   * 
   * @author yimingjin
   * @since 0.0.3
   */
  public abstract boolean isAffectedFloatMeasure();

  /**
   * 定义是否影响普通指标
   * 
   * @return
   * 
   * @author yimingjin
   * @since 0.0.3
   */
  public abstract boolean isAffectedNomalMeasure();

  @SuppressWarnings("unchecked")
  private void send(List<MeasureHost> measureHosts) {
    
    Assert.notNull(operatorSequence);
    
    if (measureHosts != null) {
      for (MeasureHost measureHost : measureHosts) {

        ProcessorKey key = new ProcessorKey(operatorSequence, this.getClass().getSimpleName(),
            measureHost.getHostType().getName(), measureHost.getId(), isAffectedFloatMeasure(),
            isAffectedNomalMeasure());

        ProcessorValue value = new ProcessorValue();

        /**
         * TODO 暂时是每个类型作为一个TOPIC，之后研究kafka的group
         */
        for (MeasureHostType type : SpringManager.getApplicationContext()
            .getBean(MeasureRepository.class).getMeasureHostTypes()) {

          SpringManager.getApplicationContext().getBean(KafkaTemplate.class).send(type.getName(),
              key, value);
        }
      }
    }
  }
}

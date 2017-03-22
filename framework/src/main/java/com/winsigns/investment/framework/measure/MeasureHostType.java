package com.winsigns.investment.framework.measure;

import org.springframework.stereotype.Component;

/**
 * Created by colin on 2017/3/6.
 */
@Component
public abstract class MeasureHostType {

  // @Autowired
  // KafKaTrigger kafKaTrigger;

  public String getName() {
    return this.getClass().getSimpleName();
  }

  @Override
  public int hashCode() {
    return getName().hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof MeasureHostType
        && this.getName().equals(((MeasureHostType) obj).getName());
  }

  // @KafkaListener(topics = {KafkaConfiguration.SEQUENCE_TOPIC})
  // public void listen(ConsumerRecord<?, ?> record) {
  //
  // String version = (String) record.key();
  // OperatorMessage operatorMessage = (OperatorMessage) record.value();
  //
  // // 遍历该MHT下的所有的指标，如果存在关心的操作，则发送消息
  // for (Measure measure : MeasureRegistry.getInstance()
  // .getMeasures(operatorMessage.getHostTypeName())) {
  // kafKaTrigger.raiseKafka(operatorMessage.getMeasureHostId(), operatorMessage.isFloat(),
  // version, measure.getName() + "." + operatorMessage.getOperatorEntityName(),
  // measure.getName());
  // }
  // }

}

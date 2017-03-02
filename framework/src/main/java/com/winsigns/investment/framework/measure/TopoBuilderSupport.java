package com.winsigns.investment.framework.measure;

import org.springframework.context.ApplicationContext;

import com.winsigns.investment.framework.kafka.KafkaConfiguration;

public abstract class TopoBuilderSupport {

  protected abstract void build(MeasureManager manager);

  public void init(ApplicationContext context) {

    MeasureManager manager = new MeasureManager();
    build(manager);
    KafkaConfiguration kafkaConfiguration = context.getBean(KafkaConfiguration.class);
    KafkaStreamTopoBuilder builder = new KafkaStreamTopoBuilder(kafkaConfiguration);
    builder.start(manager);

  }
}

package com.winsigns.investment.framework.measure.kafkaStreams;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

public class KafkaStreamsConfiguration {

  @Value("${kafka.stream.appid}")
  @Getter
  @Setter
  String appId;

  @Value("${kafka.broker.host}")
  @Getter
  @Setter
  String brokerHost;

  @Value("${kafka.zookeeper.host}")
  @Getter
  @Setter
  String zookeeperHost;

  /*
   * 创建一个为kafkastreams使用的配置bean
   */
  @Configuration
  protected static class KafkaConfiguration {

    @Bean
    public KafkaStreamsConfiguration kafkaStreamsConfiguration() {
      return new KafkaStreamsConfiguration();
    }
  }

  /*
   * 创建一个MeasureTopologyBuilder拓扑的Bean
   */
  @Configuration
  protected static class BuilderConfiguraton {
    @Bean
    MeasureTopologyBuilder measureTopologyBuilder(
        KafkaStreamsConfiguration kafkaStreamsConfiguration) {
      MeasureTopologyBuilder measureTopologyBuilder = new MeasureTopologyBuilder();
      measureTopologyBuilder.setKafkaStreamsConfiguration(kafkaStreamsConfiguration);
      return measureTopologyBuilder;
    }
  }
}

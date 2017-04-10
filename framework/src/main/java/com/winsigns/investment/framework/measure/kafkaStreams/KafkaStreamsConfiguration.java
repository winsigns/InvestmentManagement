package com.winsigns.investment.framework.measure.kafkaStreams;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

/**
 * 返回kafkastream的相关配置
 * 
 * @author yimingjin
 * @since 0.0.2
 *
 */
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

  /**
   * 
   * 创建一个为kafkastreams使用的配置bean
   * 
   * @author yimingjin
   *
   */
  @Configuration
  protected static class KafkaConfiguration {

    @Bean
    public KafkaStreamsConfiguration kafkaStreamsConfiguration() {
      return new KafkaStreamsConfiguration();
    }
  }

  /**
   * 
   * 创建一个MeasureTopologyBuilder拓扑的Bean
   * 
   * @author yimingjin
   *
   */
  @Configuration
  protected static class BuilderConfiguration {
    @Bean
    MeasureTopologyBuilder measureTopologyBuilder(
        KafkaStreamsConfiguration kafkaStreamsConfiguration) {
      MeasureTopologyBuilder measureTopologyBuilder = new MeasureTopologyBuilder();
      return measureTopologyBuilder;
    }
  }
}

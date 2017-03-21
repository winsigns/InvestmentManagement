package com.winsigns.investment.framework.kafka;

import org.springframework.beans.factory.annotation.Value;

public class KafkaConfiguration {

  @Value("${kafka.stream.appid}")
  private String appId;

  @Value("${kafka.broker.host}")
  private String brokerHost;

  @Value("${kafka.zookeeper.host}")
  private String zookeeperHost;

  public String getAppId() {
    return appId;
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }

  public String getBrokerHost() {
    return brokerHost;
  }

  public void setBrokerHost(String brokerHost) {
    this.brokerHost = brokerHost;
  }

  public String getZookeeperHost() {
    return zookeeperHost;
  }

  public void setZookeeperHost(String zookeeperHost) {
    this.zookeeperHost = zookeeperHost;
  }

  //public static final String SEQUENCE_TOPIC = "operator-sequence";

}

package com.winsigns.investment.inventoryService.measure;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.winsigns.investment.framework.measure.MeasureHost;
import com.winsigns.investment.framework.measure.MeasureHostType;
import com.winsigns.investment.inventoryService.repository.ECACashPoolRepository;

@Component
public class ECACashPoolMHT extends MeasureHostType {

  @Autowired
  ECACashPoolRepository ecaCashPoolRepository;

  @Override
  protected JpaRepository<? extends MeasureHost, Long> getRepository() {
    return ecaCashPoolRepository;
  }

  @Override
  @KafkaListener(topics = {"ECACashPoolMHT"}, group = "ECACashPoolMHT")
  public void listen(ConsumerRecord<String, String> record) {

    System.out.println("ECACashPoolMHT");

    onListen(record);

  }

}

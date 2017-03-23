package com.winsigns.investment.framework.measure.kafkaStreams;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winsigns.investment.framework.measure.ICalculateFactor;

@Component
public class KafKaTrigger {

  @Autowired
  KafkaStreamsConfiguration kafkaConfiguration;

  public void raiseKafka(JoinPoint joinPoint) throws Throwable {

    ProcessorKey key = new ProcessorKey();
    ProcessorValue value = new ProcessorValue();

    Properties config = new Properties();
    config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfiguration.getBrokerHost());
    config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
        ProcessorKeyJsonSerializer.class.getName());
    config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
        ProcessorValueJsonSerializer.class.getName());

    Long measureHostId = (Long) joinPoint.getArgs()[0];
    Boolean isFloat = (Boolean) joinPoint.getArgs()[1];
    String version = (String) joinPoint.getArgs()[2];

    ICalculateFactor measure = (ICalculateFactor) joinPoint.getThis();

    key.setMeasureHostId(measureHostId);
    key.setFloat(isFloat);
    key.setVersion(version);

    value.setName(measure.getName());

    KafkaProducer<ProcessorKey, ProcessorValue> producer =
        new KafkaProducer<ProcessorKey, ProcessorValue>(config);
    producer.send(new ProducerRecord<ProcessorKey, ProcessorValue>(measure.getName(), key, value));
    producer.close();
  }

  public void raiseKafka(Long measureHostId, boolean isFloat, String version, String operatorName,
      String topic) {

    ProcessorKey key = new ProcessorKey();
    ProcessorValue value = new ProcessorValue();

    Properties config = new Properties();
    config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfiguration.getBrokerHost());
    config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
        ProcessorKeyJsonSerializer.class.getName());
    config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
        ProcessorValueJsonSerializer.class.getName());


    key.setMeasureHostId(measureHostId);
    key.setFloat(isFloat);
    key.setVersion(version);
    key.setOperatorName(operatorName);

    value.setName(topic);

    KafkaProducer<ProcessorKey, ProcessorValue> producer =
        new KafkaProducer<ProcessorKey, ProcessorValue>(config);
    producer.send(new ProducerRecord<ProcessorKey, ProcessorValue>(topic, key, value));
    producer.close();
  }
}

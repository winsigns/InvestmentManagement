package com.winsigns.investment.framework.measure.kafkaStreams;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.winsigns.investment.framework.measure.ProcessorValue;

/**
 * 为kafkastream使用的value-serde
 * 
 * @author yimingjin
 * @since 0.0.2
 *
 */
public class ProcessorValueJsonSerde implements Serde<ProcessorValue> {

  private JsonSerializer<ProcessorValue> serializer = new JsonSerializer<ProcessorValue>();
  private JsonDeserializer<ProcessorValue> deserializer =
      new JsonDeserializer<ProcessorValue>(ProcessorValue.class);

  @Override
  public void configure(Map<String, ?> configs, boolean isKey) {}

  @Override
  public void close() {}

  @Override
  public Serializer<ProcessorValue> serializer() {
    return serializer;
  }

  @Override
  public Deserializer<ProcessorValue> deserializer() {
    return deserializer;
  }

}

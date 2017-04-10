package com.winsigns.investment.framework.measure.kafkaStreams;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.winsigns.investment.framework.measure.ProcessorKey;

/**
 * 为kafkastream使用的key-serde
 * 
 * @author yimingjin
 * @since 0.0.2
 *
 */
public class ProcessorKeyJsonSerde implements Serde<ProcessorKey> {

  private JsonSerializer<ProcessorKey> serializer = new JsonSerializer<ProcessorKey>();
  private JsonDeserializer<ProcessorKey> deserializer =
      new JsonDeserializer<ProcessorKey>(ProcessorKey.class);

  @Override
  public void configure(Map<String, ?> configs, boolean isKey) {}

  @Override
  public void close() {}

  @Override
  public Serializer<ProcessorKey> serializer() {
    return serializer;
  }

  @Override
  public Deserializer<ProcessorKey> deserializer() {
    return deserializer;
  }

}

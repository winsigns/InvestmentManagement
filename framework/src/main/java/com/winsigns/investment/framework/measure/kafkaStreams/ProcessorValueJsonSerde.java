package com.winsigns.investment.framework.measure.kafkaStreams;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class ProcessorValueJsonSerde implements Serde<ProcessorValue> {

  private JsonSerializer<ProcessorValue> serializer =
      ProcessorKeyJsonSerializer.defaultConfig(ProcessorValue.class);
  private JsonSerializer<ProcessorValue> deserializer =
      ProcessorKeyJsonSerializer.defaultConfig(ProcessorValue.class);

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

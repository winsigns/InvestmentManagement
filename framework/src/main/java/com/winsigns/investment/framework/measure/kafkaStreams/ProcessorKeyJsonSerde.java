package com.winsigns.investment.framework.measure.kafkaStreams;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class ProcessorKeyJsonSerde implements Serde<ProcessorKey> {

  private JsonSerializer<ProcessorKey> serializer =
      ProcessorKeyJsonSerializer.defaultConfig(ProcessorKey.class);
  private JsonSerializer<ProcessorKey> deserializer =
      ProcessorKeyJsonSerializer.defaultConfig(ProcessorKey.class);

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

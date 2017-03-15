package com.winsigns.investment.framework.measure.kafkaStreams;

import java.io.Closeable;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonSerializer<T> implements Closeable, AutoCloseable, Serializer<T>, Deserializer<T> {

  static private String CHARSET = "UTF-8";

  private ObjectMapper mapper;

  private Class<T> clazz;

  public JsonSerializer() {
    this(null, null);
  }

  public JsonSerializer(ObjectMapper mapper, Class<T> clazz) {
    this.mapper = mapper;
    this.clazz = clazz;
  }

  public static <T> JsonSerializer<T> defaultConfig(Class<T> clazz) {
    return new JsonSerializer<T>(new ObjectMapper(), clazz);
  }

  @Override
  public void configure(Map<String, ?> configs, boolean isKey) {
    if (mapper == null) {
      mapper = new ObjectMapper();
    }
  }

  @Override
  public byte[] serialize(String topic, T data) {
    try {
      return mapper.writeValueAsBytes(data);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException(e);
    }
  }

  @Override
  public T deserialize(String topic, byte[] data) {
    try {
      return mapper.readValue(data, clazz);
    } catch (IOException e) {
      throw new IllegalArgumentException(e);
    }
  }

  @Override
  public void close() {
    mapper = null;
  }

  public String serialize(T data) {
    String result = null;
    configure(null, true);
    byte[] tmp = serialize("serialize", data);
    try {
      result = new String(tmp, CHARSET);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    close();
    return result;
  }

  public T deserialize(String data) {
    configure(null, true);
    T result = deserialize("deserialize", data.getBytes());
    close();
    return result;
  }

}

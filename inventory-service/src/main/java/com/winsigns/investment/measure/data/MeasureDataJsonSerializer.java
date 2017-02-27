package com.winsigns.investment.measure.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;

public class MeasureDataJsonSerializer implements Closeable, AutoCloseable, Serializer<MeasureData>, Deserializer<MeasureData> {

    private ObjectMapper mapper;

    public MeasureDataJsonSerializer() {
        this(null);
    }

    public MeasureDataJsonSerializer(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public static MeasureDataJsonSerializer defaultConfig() {
        return new MeasureDataJsonSerializer(new ObjectMapper());
    }

    @Override
    public void configure(Map<String, ?> map, boolean b) {
        if(mapper == null) {
        	mapper = new ObjectMapper();
        }
    }

    @Override
    public byte[] serialize(String s, MeasureData data) {
        try {
            return mapper.writeValueAsBytes(data);
        }
        catch(JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public MeasureData deserialize(String s, byte[] bytes) {
        try {
            return mapper.readValue(bytes, MeasureData.class);
        }
        catch(IOException e) {
            throw new IllegalArgumentException(e);
        }
    }


    @Override
    public void close() {
        mapper = null;
    }

}
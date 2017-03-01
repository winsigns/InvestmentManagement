package com.winsigns.investment.framework.measure.data;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class MeasureDataJsonSerde implements Serde<MeasureData>{
	private MeasureDataJsonSerializer serializer = MeasureDataJsonSerializer.defaultConfig();
	private MeasureDataJsonSerializer deserializer = MeasureDataJsonSerializer.defaultConfig();

	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
	}

	@Override
	public void close() {
	}

	@Override
	public Serializer<MeasureData> serializer() {
		return serializer;
	}

	@Override
	public Deserializer<MeasureData> deserializer() {
		return deserializer;
	}

}

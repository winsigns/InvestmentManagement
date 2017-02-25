package com.winsigns.investment.measure;

import java.util.Properties;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.processor.TopologyBuilder;
import org.apache.kafka.streams.state.Stores;
import org.apache.log4j.Logger;

import com.winsigns.investment.measure.data.MeasureData;
import com.winsigns.investment.measure.data.MeasureDataJsonSerde;
import com.winsigns.investment.measure.data.MeasureDataJsonSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class KafkaStreamTopoBuilder {
	private KafkaStreams streams;

	@Value("${kafka.stream.appid}")
	private String appId;

	@Value("${kafka.broker.host}")
    private String brokerHost;

	@Value("${kafka.zookeeper.host}")
	private String zookeeperHost;

	private Logger log = Logger.getLogger(KafkaStreamTopoBuilder.class);

	private Serde<MeasureData> jsonSerde;

    public void start(MeasureManager measureManager){
        final StringSerializer stringSerializer = new StringSerializer();
		final StringDeserializer stringDeserializer = new StringDeserializer();
		final MeasureDataJsonSerializer jsonSerializer = MeasureDataJsonSerializer.defaultConfig();
		final MeasureDataJsonSerializer jsonDeserializer = MeasureDataJsonSerializer.defaultConfig();
		jsonSerde = new MeasureDataJsonSerde();
		
		Properties config = new Properties();
		config.put(StreamsConfig.APPLICATION_ID_CONFIG, appId);
		config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, brokerHost);
		config.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, zookeeperHost);
		config.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
		//config.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
		config.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, jsonSerde.getClass().getName());
				

		TopologyBuilder builder = new TopologyBuilder();
		
		for(String source : measureManager.getSources()){
			String name = String.format("%s.source", source);
			log.info(String.format("addSource: name:%s, source:%s", name, source));
			builder = builder.addSource(name, stringDeserializer, jsonDeserializer, source);
		}
		
		for(IMeasure measure : measureManager.getMeasures()){
			String processorName = String.format("%s.%s.processor", measure.getHost(), measure.getName());
			String stateStoreName = String.format("%s.%s.store", measure.getHost(), measure.getName());
			String sinkName = String.format("%s.%s.sink", measure.getHost(), measure.getName());
			
			log.info(String.format("addProcessor: name:%s, deps:%s", processorName, String.join(" / ", measure.getRealDependencies())));
			builder = builder.addProcessor(processorName, () -> new MeasureProcessor(measure), measure.getRealDependencies());
			log.info(String.format("addStateStore: name:%s, deps:%s", stateStoreName, processorName));
			builder = builder.addStateStore(Stores.create(stateStoreName)
					.withKeys(Serdes.String())
					.withValues(jsonSerde)
					.inMemory()
					.build(), processorName);
			log.info(String.format("addSink: name:%s, deps:%s", sinkName, processorName));
			builder = builder.addSink(sinkName, measure.getName(), stringSerializer, jsonSerializer, processorName);
		}
		
		
		streams = new KafkaStreams(builder, config);
		streams.start();
		
	}
	
	public void close(){
		streams.close();
	}

}

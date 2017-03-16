package com.winsigns.investment.framework.measure.kafkaStreams;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.processor.TopologyBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import com.winsigns.investment.framework.kafka.KafkaConfiguration;
import com.winsigns.investment.framework.measure.ICalculateFactor;
import com.winsigns.investment.framework.measure.Measure;
import com.winsigns.investment.framework.measure.MeasureRegistry;

public class MeasureTopologyBuilder {

  private static final String SOURCE_SUFFIX = ".source";
  private static final String PROCESS_SUFFIX = ".processor";
  // private static final String SINK_SUFFIX = ".sink";

  @Autowired
  KafkaConfiguration kafkaConfiguration;

  private KafkaStreams streams;

  private Serde<ProcessorKey> keySerde;

  private Serde<ProcessorValue> valueSerde;

  public void start() {
    final JsonSerializer<ProcessorKey> keyDeserializer =
        JsonSerializer.defaultConfig(ProcessorKey.class);
    final JsonSerializer<ProcessorValue> valueDeserializer =
        JsonSerializer.defaultConfig(ProcessorValue.class);

    keySerde = new ProcessorKeyJsonSerde();
    valueSerde = new ProcessorValueJsonSerde();

    Properties config = new Properties();
    config.put(StreamsConfig.APPLICATION_ID_CONFIG, kafkaConfiguration.getAppId());
    config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfiguration.getBrokerHost());
    config.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, kafkaConfiguration.getZookeeperHost());
    config.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, keySerde.getClass().getName());
    config.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, valueSerde.getClass().getName());

    TopologyBuilder builder = new TopologyBuilder();

    for (Measure measure : MeasureRegistry.getInstance().getMeasures()) {
      // 遍历指标的所有计算因子，如果不在本地指标库，则为source
      List<ICalculateFactor> factors = measure.getCalculateFactors();
      if (factors != null) {
        factors.forEach(factor -> {
          if (!MeasureRegistry.getInstance().contains(measure.getName())) {
            builder.addSource(measure.getName() + SOURCE_SUFFIX, keyDeserializer, valueDeserializer,
                measure.getName());
          }
        });
      }

      // 为本地指标建立source
      builder.addSource(measure.getName() + SOURCE_SUFFIX, keyDeserializer, valueDeserializer,
          measure.getName());

      // 为本地的指标建立process
      String processorName = measure.getName() + PROCESS_SUFFIX;
      builder.addProcessor(processorName, () -> new MeasureProcessor(measure),
          getRealDependencies(measure));
    }

    streams = new KafkaStreams(builder, config);
    streams.start();

  }

  public void close() {
    streams.close();
  }

  private String[] getRealDependencies(Measure measure) {
    ArrayList<String> calculateFactors = new ArrayList<String>();

    List<ICalculateFactor> factors = measure.getCalculateFactors();
    if (factors != null) {
      factors.forEach(factor -> {
        if (!MeasureRegistry.getInstance().contains(measure.getName())) {
          calculateFactors.add(factor.getName() + PROCESS_SUFFIX);
        } else {
          calculateFactors.add(factor.getName() + SOURCE_SUFFIX);
        }
      });
    }
    return calculateFactors.toArray(new String[calculateFactors.size()]);
  }

}

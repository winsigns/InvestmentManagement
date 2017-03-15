package com.winsigns.investment.framework.measure.kafkaStreams;

import java.util.ArrayList;
import java.util.Properties;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.processor.TopologyBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import com.winsigns.investment.framework.kafka.KafkaConfiguration;
import com.winsigns.investment.framework.measure.CalculateFactor;
import com.winsigns.investment.framework.measure.IMeasure;
import com.winsigns.investment.framework.measure.Measure;
import com.winsigns.investment.framework.measure.MeasureRegistry;
import com.winsigns.investment.framework.measure.MockMeasure;

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
    // final JsonSerializer<ProcessorKey> keySerializer =
    // JsonSerializer.defaultConfig(ProcessorKey.class);
    final JsonSerializer<ProcessorKey> keyDeserializer =
        JsonSerializer.defaultConfig(ProcessorKey.class);
    // final JsonSerializer<ProcessorValue> valueSerializer =
    // JsonSerializer.defaultConfig(ProcessorValue.class);
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

    for (IMeasure measure : MeasureRegistry.getInstance().getMeasures()) {
      if (measure instanceof Measure) {
        Measure measure_ = (Measure) measure;

        builder.addSource(measure_.getFullName() + SOURCE_SUFFIX, keyDeserializer,
            valueDeserializer, measure_.getFullName());

        if (measure_.getCalculateFactors() != null) {
          measure_.getCalculateFactors().forEach(factor -> {
            if (factor instanceof MockMeasure) {
              builder.addSource(measure_.getFullName() + SOURCE_SUFFIX, keyDeserializer,
                  valueDeserializer, measure_.getFullName());
            }

          });
        }
      }
    }

    for (IMeasure measure : MeasureRegistry.getInstance().getMeasures()) {
      if (measure instanceof Measure) {
        Measure measure_ = (Measure) measure;

        String processorName = measure_.getFullName() + PROCESS_SUFFIX;
        // String sinkName = measure_.getFullName() + SINK_SUFFIX;

        builder.addProcessor(processorName, () -> new MeasureProcessor(measure),
            getRealDependencies(measure_));
        // .addSink(sinkName, measure_.getName(), keySerializer, valueSerializer, processorName);
      }
    }

    streams = new KafkaStreams(builder, config);
    streams.start();

  }

  public void close() {
    streams.close();
  }

  private String[] getRealDependencies(Measure measure) {
    ArrayList<String> calculateFactors = new ArrayList<String>();

    if (measure.getCalculateFactors() != null) {
      measure.getCalculateFactors().forEach(factor -> {
        if (factor instanceof Measure) {
          calculateFactors.add(factor.getFullName() + PROCESS_SUFFIX);
        } else if (factor instanceof MockMeasure) {
          calculateFactors.add(factor.getFullName() + SOURCE_SUFFIX);
        } else if (factor instanceof CalculateFactor) {
          calculateFactors.add(factor.getFullName() + SOURCE_SUFFIX);
        }
      });
    }
    return calculateFactors.toArray(new String[calculateFactors.size()]);
  }

}

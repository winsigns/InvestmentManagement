package com.winsigns.investment.framework.measure.kafkaStreams;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.processor.TopologyBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.winsigns.investment.framework.measure.IMeasure;
import com.winsigns.investment.framework.measure.Measure;
import com.winsigns.investment.framework.measure.MeasureRepository;
import com.winsigns.investment.framework.measure.ProcessorKey;
import com.winsigns.investment.framework.measure.ProcessorValue;
import com.winsigns.investment.framework.model.OperatorEntity;

/*
 * 
 */

/**
 * 指标的拓扑构建
 * <p>
 * 实现SmartInitializingSingleton是为了其start一定在其他measure的bean构建完成之后运行
 * 详见DefaultListableBeanFactory.preInstantiateSingletons
 * 
 * @author yimingjin
 * @since 0.0.2
 */
public class MeasureTopologyBuilder implements SmartInitializingSingleton {

  private static final String SOURCE_SUFFIX = ".source";
  private static final String PROCESS_SUFFIX = ".processor";
  private static final String SINK_SUFFIX = ".sink";

  private Logger log = LoggerFactory.getLogger(MeasureTopologyBuilder.class);

  @Autowired
  KafkaStreamsConfiguration kafkaStreamsConfiguration;

  @Autowired
  MeasureRepository measureRepository;

  @Autowired
  KafkaTemplate<ProcessorKey, ProcessorValue> kafkaTemplate;

  private KafkaStreams streams;

  private Serde<ProcessorKey> keySerde;

  private Serde<ProcessorValue> valueSerde;

  public void start() {

    final JsonSerializer<ProcessorKey> keySerializer = new JsonSerializer<ProcessorKey>();
    final JsonSerializer<ProcessorValue> valueSerializer = new JsonSerializer<ProcessorValue>();
    final JsonDeserializer<ProcessorKey> keyDeserializer =
        new JsonDeserializer<ProcessorKey>(ProcessorKey.class);
    final JsonDeserializer<ProcessorValue> valueDeserializer =
        new JsonDeserializer<ProcessorValue>(ProcessorValue.class);

    keySerde = new ProcessorKeyJsonSerde();
    valueSerde = new ProcessorValueJsonSerde();

    Properties config = new Properties();
    config.put(StreamsConfig.APPLICATION_ID_CONFIG, kafkaStreamsConfiguration.getAppId());
    config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaStreamsConfiguration.getBrokerHost());
    config.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG,
        kafkaStreamsConfiguration.getZookeeperHost());
    config.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, keySerde.getClass().getName());
    config.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, valueSerde.getClass().getName());

    TopologyBuilder builder = new TopologyBuilder();

    log.info("creating measure topo:");

    HashSet<String> sourceNames = new HashSet<String>();
    for (Measure thisMeasure : measureRepository.getMeasures()) {
      // 查看指标是否关心某个操作，如果关心，则为其增加一个源
      Class<? extends OperatorEntity> operator = thisMeasure.getConcernedOperator();
      if (operator != null) {
        log.info(String.format("addSource: name:%s, source:%s",
            thisMeasure.getFullName() + SOURCE_SUFFIX,
            thisMeasure.getFullName() + "." + operator.getSimpleName()));
        builder.addSource(thisMeasure.getFullName() + SOURCE_SUFFIX, keyDeserializer,
            valueDeserializer, thisMeasure.getFullName() + "." + operator.getSimpleName());
        sourceNames.add(thisMeasure.getFullName());
      }

      // 遍历指标的所有依赖，如果不在本地指标库，则为source
      List<IMeasure> measures = thisMeasure.getDependentMeasure();
      if (measures != null) {
        for (IMeasure measure : measures) {
          if (!measureRepository.contains(measure.getFullName())) {
            // 如果没有创建过该source，则创建
            if (!sourceNames.contains(measure.getFullName())) {
              log.info(String.format("addSource: name:%s, source:%s",
                  measure.getFullName() + SOURCE_SUFFIX, measure.getFullName()));
              builder.addSource(measure.getFullName() + SOURCE_SUFFIX, keyDeserializer,
                  valueDeserializer, measure.getFullName());
              sourceNames.add(measure.getFullName());
            }
          }
        }
      }

      // 为本地的指标建立process
      String processorName = thisMeasure.getFullName() + PROCESS_SUFFIX;
      String sinkName = thisMeasure.getFullName() + SINK_SUFFIX;
      log.info(String.format("addProcessor: name:%s, deps:%s", processorName,
          String.join(" / ", getRealDependencies(thisMeasure))));
      builder.addProcessor(processorName, () -> new MeasureProcessor(thisMeasure),
          getRealDependencies(thisMeasure));
      log.info(String.format("addSink: name:%s, deps:%s", sinkName, processorName));
      builder.addSink(sinkName, thisMeasure.getFullName(), keySerializer, valueSerializer,
          processorName);
    }

    log.info("created measure topo:");

    streams = new KafkaStreams(builder, config);

    // 防止没有任何指标的时候，启动
    if (!builder.sourceTopics(kafkaStreamsConfiguration.getAppId()).isEmpty()) {

      // TODO 这里要先检查kafka的主题是否存在，不然会启动失败
      // 先往各个topic中发送一条消息，创建主题
      for (String topic : builder.sourceTopics(kafkaStreamsConfiguration.getAppId())) {
        kafkaTemplate.send(topic, new ProcessorKey(null, ProcessorKey.INIT), new ProcessorValue());
      }
      streams.start();
    }

  }

  public void close() {
    streams.close();
  }

  /**
   * 为构建拓扑扩展指标的依赖
   * 
   * @param measure
   * @return
   */
  private String[] getRealDependencies(Measure measure) {
    ArrayList<String> dependencies = new ArrayList<String>();

    List<IMeasure> measures = measure.getDependentMeasure();
    if (measures != null) {
      measures.forEach(dependentMeasure -> {
        if (!measureRepository.contains(dependentMeasure.getFullName())) {
          dependencies.add(dependentMeasure.getFullName() + SOURCE_SUFFIX);
        } else {
          dependencies.add(dependentMeasure.getFullName() + PROCESS_SUFFIX);
        }
      });
    }

    if (measure.getConcernedOperator() != null) {
      dependencies.add(measure.getFullName() + SOURCE_SUFFIX);
    }

    return dependencies.toArray(new String[dependencies.size()]);
  }

  /**
   * 当全部bean构建完成之后，由spring统一执行，保证所有指标已经被注册
   */
  @Override
  public void afterSingletonsInstantiated() {
    start();
  }
}

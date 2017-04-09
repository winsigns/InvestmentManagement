package com.winsigns.investment.framework.measure.kafkaStreams;

import org.apache.kafka.streams.processor.AbstractProcessor;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winsigns.investment.framework.measure.Measure;
import com.winsigns.investment.framework.measure.MeasureValue;
import com.winsigns.investment.framework.measure.ProcessorKey;
import com.winsigns.investment.framework.measure.ProcessorValue;

/**
 * 指标处理器
 * 
 * @author yimingjin
 * @since 0.0.2
 */
public class MeasureProcessor extends AbstractProcessor<ProcessorKey, ProcessorValue> {

  private Logger log = LoggerFactory.getLogger(MeasureProcessor.class);

  private Measure measure;

  public MeasureProcessor(Measure measure) {
    this.measure = measure;
  }

  @Override
  public void init(ProcessorContext context) {
    super.init(context);
  }

  @Override
  public void process(ProcessorKey key, ProcessorValue value) {

    // 如果是初始化，则略过
    if (key.getOperatorName().equals(ProcessorKey.INIT)) {
      return;
    }

    try {
      MeasureValue measureValue = measure.calculate(key, value);

      if (measureValue != null) {
        context().forward(key, value);
      }
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }
}

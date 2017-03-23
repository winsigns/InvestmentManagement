package com.winsigns.investment.framework.measure.kafkaStreams;

import org.apache.kafka.streams.processor.AbstractProcessor;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winsigns.investment.framework.measure.Measure;
import com.winsigns.investment.framework.measure.TradingMeasureValue;

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

    try {
      /*
       * 如果该指标关心该操作
       */
      if ((key.getOperatorName() != null && measure.isConcerned(key.getOperatorName()))
          || key.getOperatorName() == null) {

        TradingMeasureValue tradingMeasureValue =
            measure.calculate(key.getMeasureHostId(), key.isFloat(), key.getVersion());

        if (tradingMeasureValue != null) {
          key.setMeasureHostId(tradingMeasureValue.getMeasureHost().getId());
          key.setOperatorName(null); // 一旦计算过一次之后，就不再关心了
          value.setName(tradingMeasureValue.getMeasure().getName());
          context().forward(key, value);
        }
      }
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }
}

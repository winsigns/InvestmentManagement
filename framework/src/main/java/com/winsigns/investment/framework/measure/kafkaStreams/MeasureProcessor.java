package com.winsigns.investment.framework.measure.kafkaStreams;

import org.apache.kafka.streams.processor.AbstractProcessor;
import org.apache.kafka.streams.processor.ProcessorContext;

import com.winsigns.investment.framework.measure.Measure;
import com.winsigns.investment.framework.measure.TradingMeasureValue;

public class MeasureProcessor extends AbstractProcessor<ProcessorKey, ProcessorValue> {

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
    TradingMeasureValue tradingMeasureValue =
        measure.calculate(key.getMeasureHostId(), key.isFloat(), key.getVersion());
    value.setName(tradingMeasureValue.getMeasure().getName());
    context().forward(key, value);
  }
}

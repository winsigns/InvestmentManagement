package com.winsigns.investment.framework.measure.kafkaStreams;

import org.apache.kafka.streams.processor.AbstractProcessor;
import org.apache.kafka.streams.processor.ProcessorContext;

import com.winsigns.investment.framework.measure.IMeasure;
import com.winsigns.investment.framework.measure.TradingMeasureValue;

public class MeasureProcessor extends AbstractProcessor<ProcessorKey, ProcessorValue> {

  private IMeasure measure;

  public MeasureProcessor(IMeasure measure) {
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
    value.setName(tradingMeasureValue.getMeasure().getFullName());
    context().forward(key, value);
  }
}

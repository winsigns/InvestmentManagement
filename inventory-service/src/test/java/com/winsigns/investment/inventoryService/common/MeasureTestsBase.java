package com.winsigns.investment.inventoryService.common;

import com.winsigns.investment.framework.measure.MeasureValueRepository;
import org.mockito.Mock;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * Created by jmx on 4/10/17.
 */
public class MeasureTestsBase {
  @Mock
  protected MeasureValueRepository measureValueRepository;
  @Mock
  protected KafkaTemplate<Object, Object> kafkaTemplate;

}

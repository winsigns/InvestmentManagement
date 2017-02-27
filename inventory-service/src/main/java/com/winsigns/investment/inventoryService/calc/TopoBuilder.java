package com.winsigns.investment.inventoryService.calc;

import com.winsigns.investment.inventoryService.calc.index.position.OpenPositionCost;
import com.winsigns.investment.inventoryService.calc.index.position.PositionVolume;
import com.winsigns.investment.measure.KafkaStreamTopoBuilder;
import com.winsigns.investment.measure.MeasureManager;
import org.springframework.context.ApplicationContext;

/**
 * Created by jmx on 2017/2/25.
 */
public class TopoBuilder {

  private MeasureManager build() {
    MeasureManager manager = new MeasureManager();

    manager.addMeasure(new OpenPositionCost());
    manager.addMeasure(new PositionVolume());

    return manager;
  }

  public void init(ApplicationContext context) {
    MeasureManager manager = build();
    KafkaStreamTopoBuilder builder = context.getBean(KafkaStreamTopoBuilder.class);
    builder.start(manager);
  }
}

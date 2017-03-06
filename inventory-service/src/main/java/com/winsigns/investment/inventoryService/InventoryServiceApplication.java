package com.winsigns.investment.inventoryService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;

import com.winsigns.investment.framework.measure.MeasureManager;
import com.winsigns.investment.framework.measure.TopoBuilderSupport;
import com.winsigns.investment.inventoryService.calc.index.position.OpenPositionCost;
import com.winsigns.investment.inventoryService.calc.index.position.PositionVolume;

@SpringBootApplication
@EnableDiscoveryClient
@EnableHypermediaSupport(type = {HypermediaType.HAL})
@ComponentScan({"com.winsigns.investment.inventoryService", "com.winsigns.investment.measure"})
public class InventoryServiceApplication {

  public static class TopoBuilder extends TopoBuilderSupport {

    @Override
    protected void build(MeasureManager manager) {
      manager.addMeasure(new OpenPositionCost());
      manager.addMeasure(new PositionVolume());
    }
  }

  public static void main(String[] args) {

    ApplicationContext context = SpringApplication.run(InventoryServiceApplication.class, args);

    TopoBuilder builder = new TopoBuilder();
    builder.init(context);
  }
}

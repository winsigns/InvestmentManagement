package com.winsigns.investment.inventoryService;

import com.winsigns.investment.inventoryService.calc.TopoBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;

@SpringBootApplication
@EnableHypermediaSupport(type = { HypermediaType.HAL })
@ComponentScan("com.winsigns.investment.measure")
public class InventoryServiceApplication {

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(InventoryServiceApplication.class, args);

		TopoBuilder builder = new TopoBuilder();
		builder.init(context);
	}
}

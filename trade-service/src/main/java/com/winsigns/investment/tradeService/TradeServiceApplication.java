package com.winsigns.investment.tradeService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;

@SpringBootApplication
@EnableDiscoveryClient
@EnableHypermediaSupport(type = { HypermediaType.HAL })
public class TradeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradeServiceApplication.class, args);
	}
}

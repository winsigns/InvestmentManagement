package com.winsigns.investment.fundService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;

@SpringBootApplication
@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableHypermediaSupport(type= {HypermediaType.HAL})
public class FundServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FundServiceApplication.class, args);
	}
}

package com.winsigns.investment.fundService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class FundServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FundServiceApplication.class, args);
	}
}

package com.winsigns.investment.inventoryService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class InventoryServiceApplication {
	
	public static void main(String[] args) {
		
		SpringApplication.run(InventoryServiceApplication.class, args);
	}
}

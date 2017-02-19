package com.winsigns.investment.inventoryService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.winsigns.investment.inventoryService.service.CurrencyService;

@RestController
@RequestMapping("/currencies")
public class CurrencyController {

	@Autowired
	CurrencyService currencyService;
}

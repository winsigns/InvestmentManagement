package com.winsigns.investment.webGateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/hello")
public class FundController {

	@GetMapping
	public String getHello(){
		return "hello word";
	}
}

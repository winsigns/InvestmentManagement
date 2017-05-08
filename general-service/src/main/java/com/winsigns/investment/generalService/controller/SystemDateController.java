package com.winsigns.investment.generalService.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.winsigns.investment.generalService.resource.DateResource;
import com.winsigns.investment.generalService.resource.DateResourceAssembler;
import com.winsigns.investment.generalService.service.ChronoService;

@RestController
@RequestMapping("/system-date")
public class SystemDateController {

  @Autowired
  ChronoService chronoService;

  @GetMapping
  public DateResource getSystemDate() {

    Date date = chronoService.getSystemDate();

    return new DateResourceAssembler().toResource(date);
  }

}

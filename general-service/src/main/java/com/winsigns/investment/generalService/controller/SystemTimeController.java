package com.winsigns.investment.generalService.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.winsigns.investment.generalService.resource.TimeResource;
import com.winsigns.investment.generalService.resource.TimeResourceAssembler;
import com.winsigns.investment.generalService.service.ChronoService;

@RestController
@RequestMapping("/system-time")
public class SystemTimeController {

  @Autowired
  ChronoService chronoService;

  @GetMapping
  public TimeResource getSystemTime() {

    Date date = chronoService.getSystemTime();

    return new TimeResourceAssembler().toResource(date);
  }

}

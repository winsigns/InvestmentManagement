package com.winsigns.investment.generalService.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.Date;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.winsigns.investment.generalService.controller.SystemTimeController;

public class TimeResourceAssembler extends ResourceAssemblerSupport<Date, TimeResource> {

  public TimeResourceAssembler() {
    super(SystemTimeController.class, TimeResource.class);
  }

  @Override
  public TimeResource toResource(Date date) {

    TimeResource dateResource = instantiateResource(date);
    dateResource.add(linkTo(SystemTimeController.class).withSelfRel());

    return dateResource;
  }

  @Override
  protected TimeResource instantiateResource(Date entity) {
    return new TimeResource(entity);
  }
}

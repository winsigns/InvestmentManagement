package com.winsigns.investment.generalService.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.Date;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.winsigns.investment.generalService.controller.SystemDateController;

public class DateResourceAssembler extends ResourceAssemblerSupport<Date, DateResource> {

  public DateResourceAssembler() {
    super(SystemDateController.class, DateResource.class);
  }

  @Override
  public DateResource toResource(Date date) {

    DateResource dateResource = instantiateResource(date);
    dateResource.add(linkTo(SystemDateController.class).withSelfRel());

    return dateResource;
  }

  @Override
  protected DateResource instantiateResource(Date entity) {
    return new DateResource(entity);
  }
}

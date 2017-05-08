package com.winsigns.investment.generalService.resource;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.winsigns.investment.framework.hal.ResourceSupport;

import lombok.Getter;

@Relation(value = "date", collectionRelation = "dates")
public class DateResource extends ResourceSupport {

  @JsonIgnore
  private static final DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

  @Getter
  private final String date;

  public DateResource(Date date) {
    this.date = dateFormat.format(date);
  }

}

package com.winsigns.investment.generalService.resource;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.winsigns.investment.framework.hal.ResourceSupport;

import lombok.Getter;

@Relation(value = "time", collectionRelation = "times")
public class TimeResource extends ResourceSupport {

  @JsonIgnore
  private static final DateFormat timeFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss.sss");

  @Getter
  private final String time;

  public TimeResource(Date date) {
    this.time = timeFormat.format(date);
  }

}

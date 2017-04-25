package com.winsigns.investment.inventoryService.command;

import java.util.List;

import com.winsigns.investment.inventoryService.model.CapitalSerial;
import com.winsigns.investment.inventoryService.model.PositionSerial;

import lombok.Data;

@Data
public class ResponseResourceApplication {

  @Data
  public static class Header {

    Boolean result = true;

    String code;

    String message;
  }

  private Header header;

  private Long virtualDoneId;

  private Long applicationFormId;

  List<PositionSerial> positionSerials;

  List<CapitalSerial> capitalSerials;

}

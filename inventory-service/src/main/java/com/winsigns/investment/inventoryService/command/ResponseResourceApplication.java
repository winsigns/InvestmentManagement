package com.winsigns.investment.inventoryService.command;

import java.util.List;

import com.winsigns.investment.inventoryService.model.CapitalSerial;
import com.winsigns.investment.inventoryService.model.PositionSerial;

import lombok.Data;

@Data
public class ResponseResourceApplication {

  private Long virtualDoneId;

  private Long applicationFormId;

  List<PositionSerial> positionSerials;

  List<CapitalSerial> capitalSerials;

}

package com.winsigns.investment.inventoryService.command;

import lombok.Data;

@Data
public class ResponseResourceApplication {

  @Data
  public static class Header {

    Boolean result = true;

    String code;

    String message;
  }

  private Header header = new Header();

  private Long virtualDoneId;

  private Long applicationFormId;

  private Long instructionId;

}

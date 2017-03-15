package com.winsigns.investment.framework.measure.kafkaStreams;

import lombok.Data;

@Data
public class ProcessorKey {

  private Long measureHostId;

  private String version;

  private boolean isFloat;

}

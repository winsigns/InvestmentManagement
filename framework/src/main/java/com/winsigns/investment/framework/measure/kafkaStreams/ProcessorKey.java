package com.winsigns.investment.framework.measure.kafkaStreams;

import lombok.Data;

@Data
public class ProcessorKey {

  private String name;

  private Long measureHostId;

  private String version;

  private boolean isFloat;

  // private String topic;

}

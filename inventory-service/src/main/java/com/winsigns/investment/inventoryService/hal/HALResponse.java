package com.winsigns.investment.inventoryService.hal;

import java.util.HashMap;
import java.util.Map;

import org.springframework.hateoas.Resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class HALResponse<T> extends Resource<T> {
  public HALResponse(T content) {
    super(content);
    this.embedded = new HashMap<>();
  }

  @JsonProperty("_embedded")
  private Map<String, Object> embedded;

  public void add(String name, Object embedded) {

    this.embedded.put(name, embedded);
  }

  public Map<String, Object> getEmbedded() {
    return embedded;
  }

  public void setEmbedded(Map<String, Object> embedded) {
    this.embedded = embedded;
  }
}

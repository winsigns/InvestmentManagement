package com.winsigns.investment.inventoryService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.winsigns.investment.framework.measure.kafkaStreams.JsonSerializer;
import com.winsigns.investment.inventoryService.command.ApplyInventoryCommand;


@Service
public class InventoryService {

  @Autowired
  private StringRedisTemplate redisTemplate;

  private JsonSerializer<ApplyInventoryCommand> serializer =
      new JsonSerializer<ApplyInventoryCommand>();

  public void apply(ApplyInventoryCommand applyInventoryCommand) {
    String apply = serializer.serialize(applyInventoryCommand);
    // applyInventoryCommand = serializer.deserialize(apply);
    redisTemplate.opsForList().leftPush("apply-queue", apply);
  }

}

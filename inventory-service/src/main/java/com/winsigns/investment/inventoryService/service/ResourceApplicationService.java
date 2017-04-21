package com.winsigns.investment.inventoryService.service;

import java.sql.Timestamp;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.winsigns.investment.inventoryService.capital.common.CapitalServiceManager;
import com.winsigns.investment.inventoryService.capital.common.ICapitalService;
import com.winsigns.investment.inventoryService.command.ApplyResourceCommand;
import com.winsigns.investment.inventoryService.exception.ResourceApplicationExcepiton;
import com.winsigns.investment.inventoryService.integration.FundServiceIntegration;
import com.winsigns.investment.inventoryService.model.ResourceApplicationForm;
import com.winsigns.investment.inventoryService.model.ResourceApplicationForm.ApplyStatus;
import com.winsigns.investment.inventoryService.position.common.IPositionService;
import com.winsigns.investment.inventoryService.position.common.PositionServiceManager;

import lombok.extern.slf4j.Slf4j;

/**
 * 资源申请服务
 * <p>
 * 资源申请单根据产品账户通过redis的队列进行维护<br>
 * 每次申请插入两条记录，仅有status字段不同<br>
 * 线程的主逻辑中，每个时间片，遍历所有的产品队列<br>
 * 如果队首记录的status为INIT，则pop出该记录进行业务逻辑处理,处理完，再pop出PROCESSING的记录<br>
 * 否则跳过这次循环<br>
 * 这样的处理逻辑的目的，保证多进程处理的时候，同一资源队列只有一条单子在处理
 * 
 * @author yimingjin
 *
 */
@Service
@Slf4j
public class ResourceApplicationService extends Thread implements SmartInitializingSingleton {
  @Bean
  private RedisTemplate<String, ResourceApplicationForm> inventoryTemplate(
      RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<String, ResourceApplicationForm> template =
        new RedisTemplate<String, ResourceApplicationForm>();
    RedisSerializer<String> stringSerializer = new StringRedisSerializer();
    GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();
    template.setConnectionFactory(redisConnectionFactory);
    template.setKeySerializer(stringSerializer);
    template.setValueSerializer(serializer);
    template.setHashKeySerializer(stringSerializer);
    template.setHashValueSerializer(serializer);

    return template;
  }

  @Autowired
  RedisTemplate<String, ResourceApplicationForm> inventoryTemplate;

  @Autowired
  StringRedisTemplate keyTemplate;

  @Autowired
  FundServiceIntegration fundServiceIntegration;

  @Autowired
  PositionServiceManager positionServiceManager;

  @Autowired
  CapitalServiceManager capitalServiceManager;

  @Autowired
  KafkaTemplate kafkaTemplate;

  static final String applyKey = "fund-accounts";
  static final String applyKeyTemp = "fund-accounts:%d";
  static final String applyTopic = "resource-application";

  /**
   * 接受一条资源申请
   * 
   * @param applyInventoryCommand
   */
  public void apply(ApplyResourceCommand applyInventoryCommand) {

    ResourceApplicationForm form = new ResourceApplicationForm();
    form.setPortfolioId(applyInventoryCommand.getPortfolioId());
    form.setSecurityId(applyInventoryCommand.getSecurityId());
    form.setCurrency(applyInventoryCommand.getCurrency());
    form.setAppliedCapital(applyInventoryCommand.getAppliedCapital());
    form.setAppliedPosition(applyInventoryCommand.getPortfolioId());
    form.setAppliedTime(new Timestamp(System.currentTimeMillis()));

    ResourceApplicationForm formStatus = form.clone();
    formStatus.setStatus(ApplyStatus.PROCESSING);
    Long fundAccountId =
        fundServiceIntegration.getFundAccountId(applyInventoryCommand.getPortfolioId());

    String key = String.format(applyKeyTemp, fundAccountId);
    keyTemplate.boundSetOps(applyKey).add(key);
    inventoryTemplate.boundListOps(key).leftPushAll(form, formStatus);
  }

  private final Long sleepTime = 100L;

  /**
   * 主函数轮询资源申请单
   */
  @Override
  public void run() {
    log.info("线程:" + Thread.currentThread().getName() + "运行中.....");
    while (this.isAlive()) {
      try {
        sleep(sleepTime);

        Set<String> keys = keyTemplate.boundSetOps(applyKey).members();
        for (String key : keys) {
          ResourceApplicationForm form = null;
          form = inventoryTemplate.boundListOps(key).index(-1);
          // 可以工作
          if (form != null && form.getStatus().equals(ApplyStatus.INIT)) {
            form = inventoryTemplate.boundListOps(key).rightPop(1, TimeUnit.MILLISECONDS);
            processForm(form);
            inventoryTemplate.boundListOps(key).rightPop(1, TimeUnit.MILLISECONDS);
          }
        }
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();

      }

    }
  }

  @Override
  public void afterSingletonsInstantiated() {
    this.start();
  }

  public enum ErrorCode {
    // 不支持的资金服务
    NOT_SUPPORT_CAPITAL_SERVICE,
    // 不支持的期货服务
    NOT_SUPPORT_POSITION_SERVICE;

    public String toString() {
      return "ResourceApplicationService:" + this.name();
    }
  }

  /**
   * 处理申请单
   * 
   * @param form
   */
  public void processForm(ResourceApplicationForm form) {

    try {
      IPositionService positionService =
          positionServiceManager.getService(form.getPositionService());
      if (positionService == null) {
        throw new ResourceApplicationExcepiton(ErrorCode.NOT_SUPPORT_POSITION_SERVICE.toString());
      }

      positionService.apply(form.getPortfolioId(), form.getSecurityId(), null,
          form.getAppliedPosition());

      ICapitalService capitalService = capitalServiceManager.getService(form.getCapitalService());
      if (capitalService == null) {
        throw new ResourceApplicationExcepiton(ErrorCode.NOT_SUPPORT_CAPITAL_SERVICE.toString());
      }

      capitalService.apply(fundServiceIntegration.getFundAccountId(form.getPortfolioId()),
          form.getCurrency(), form.getAppliedCapital());

      kafkaTemplate.send(applyTopic, "true", "true");

    } catch (ResourceApplicationExcepiton e) {

      kafkaTemplate.send(applyTopic, "false", e.getMessage());
    }

  }
}

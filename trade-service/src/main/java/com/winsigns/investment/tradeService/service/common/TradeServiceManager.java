package com.winsigns.investment.tradeService.service.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.winsigns.investment.framework.spring.SpringManager;
import com.winsigns.investment.tradeService.command.CommitInstructionCommand;
import com.winsigns.investment.tradeService.model.Done;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TradeServiceManager {

  List<ITradeService> services = new ArrayList<ITradeService>();

  static public TradeServiceManager getTradeServiceManager() {
    return SpringManager.getApplicationContext().getBean(TradeServiceManager.class);
  }

  /**
   * 将交易服务注册到该管理者中
   * 
   * @param service
   */
  public void register(ITradeService service) {
    services.add(service);
  }

  /**
   * 获取指定名字的交易服务
   * 
   * @param name 交易服务名
   * @return 指定交易服务
   */
  public ITradeService getService(String name) {
    for (ITradeService service : services) {
      if (service.getName().equals(name)) {
        return service;
      }
    }
    return null;
  }

  /**
   * 获取所有的交易服务
   * 
   * @return 交易服务列表
   */
  public List<ITradeService> getServices() {
    return services;
  }

  /**
   * 获取特定投资服务可用的交易服务列表
   * 
   * @param investService
   * @return
   */
  public List<ITradeService> getAvailableTradeServices(String investService, Long securityId) {
    Assert.notNull(investService);
    Assert.notNull(securityId);

    // TODO securityId 标的的校验待补全

    List<ITradeService> result = new ArrayList<ITradeService>();

    for (ITradeService service : services) {
      if (service.getSupportedInvestService().getName().equals(investService)) {
        result.add(service);
      }
    }
    return result;
  }

  /**
   * 接受一条指令，创建一个虚拟成交，向清单服务发送资源申请
   * 
   * @param command
   */
  public boolean acceptInstruction(CommitInstructionCommand command) {

    Resource resource = chooseTradeService(command);

    resource.getService().virtualDone(command, resource);

    return true;
  }

  /**
   * 选择一个最合适的,并返回需要的资源
   * 
   * @param services
   * @return
   */
  protected Resource chooseTradeService(CommitInstructionCommand command) {

    List<ITradeService> services =
        this.getAvailableTradeServices(command.getInvestService(), command.getSecurityId());
    Assert.notNull(services);
    Assert.notEmpty(services);

    // TODO 目前应该只有一个 暂时写死
    return services.get(0).calculateRequiredResource(command);
  }

  public void done(Done thisDone) {

    ITradeService service = this.getService(thisDone.getEntrust().getTradeService());

    service.done(thisDone);
  }

  /**
   * 处理
   */
  @KafkaListener(topics = {"resource-application"})
  public void respnseResourceApplication(ConsumerRecord<String, String> record) {
    log.debug(record.key());
    log.debug(record.value());
  }
}

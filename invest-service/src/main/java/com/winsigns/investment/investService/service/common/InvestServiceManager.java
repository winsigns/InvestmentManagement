package com.winsigns.investment.investService.service.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.winsigns.investment.framework.spring.SpringManager;
import com.winsigns.investment.investService.model.Instruction;

/**
 * 投资服务的管理者
 * 
 * @author yimingjin
 *
 */
@Component
public class InvestServiceManager {

  static public InvestServiceManager getInvestServiceManager() {
    return SpringManager.getApplicationContext().getBean(InvestServiceManager.class);
  }

  private List<IInvestService> services = new ArrayList<IInvestService>();

  /**
   * 将投资服务注册到该管理者中
   * 
   * @param service
   */
  public void register(IInvestService service) {
    services.add(service);
  }

  /**
   * 获取指定名字的投资服务
   * 
   * @param name 投资服务名
   * @return 指定投资服务 如果没有找到，返回默认的投资服务
   */
  public IInvestService getService(String name) {
    for (IInvestService service : services) {
      if (service.getName().equals(name)) {
        return service;
      }
    }
    return new DefaultInvestService();
  }

  /**
   * 获取所有的投资服务信息
   * 
   * @return 返回服务名和服务支持的方向
   */
  public List<IInvestService> getServices() {
    return services;
  }

  /**
   * 提交指令
   * 
   * @param instruction 具体的指令
   * @return
   */
  public boolean commitInstruction(Instruction instruction) {

    IInvestService service = this.getService(instruction.getInvestService());
    Assert.notNull(service);
    return service.commitInstruction(instruction);

  }
}

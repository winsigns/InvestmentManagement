package com.winsigns.investment.investService.service.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

import com.winsigns.investment.investService.model.Instruction;

/**
 * 投资服务的管理者
 * 
 * @author yimingjin
 *
 */
public class InvestServiceManager {

  private List<IInvestService> services;

  private static InvestServiceManager instance = new InvestServiceManager();

  public static InvestServiceManager getInstance() {
    return instance;
  }

  private InvestServiceManager() {
    services = new ArrayList<IInvestService>();
  }

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
   * @return 指定投资服务
   */
  public IInvestService getService(String name) {
    for (IInvestService service : services) {
      if (service.getName().equals(name)) {
        return service;
      }
    }
    return null;
  }

  /**
   * 获取所有的投资服务信息
   * 
   * @return 返回服务名和服务支持的方向
   */
  public Map<IInvestService, Enum<?>[]> getServicesInfo() {
    Map<IInvestService, Enum<?>[]> serviceDetails = new HashMap<IInvestService, Enum<?>[]>();

    for (IInvestService service : services) {
      serviceDetails.put(service, service.getInstructionType());
    }
    return serviceDetails;
  }

  public void commitInstruction(Instruction instruction) {

    IInvestService service = this.getService(instruction.getInvestService());
    Assert.notNull(service);
    service.commitInstruction(instruction);

  }
}

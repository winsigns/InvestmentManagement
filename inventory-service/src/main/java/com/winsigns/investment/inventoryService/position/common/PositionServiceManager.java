package com.winsigns.investment.inventoryService.position.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class PositionServiceManager {

  List<IPositionService> services = new ArrayList<IPositionService>();

  /**
   * 将持仓服务注册到该管理者中
   * 
   * @param service
   */
  public void register(IPositionService service) {
    services.add(service);
  }

  /**
   * 获取名字的持仓服务
   * 
   * @param name 持仓服务
   * @return 指定持仓服务
   */
  public IPositionService getService(String name) {
    for (IPositionService service : services) {
      if (service.getName().equals(name)) {
        return service;
      }
    }
    return null;
  }
}

package com.winsigns.investment.tradeService.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winsigns.investment.tradeService.command.CommitInstructionCommand;

@Service
public class TradeServiceManager {

  @Autowired
  SSEAStockTradeService sseAStockTradeService;

  public void acceptInstruction(CommitInstructionCommand commitInstructionCmd) {

    List<TradeServiceBase> services =
        getAvailableTradeServices(commitInstructionCmd.getInvestSvc());

    Double maxCapital = 0.0;
    Integer maxPosition = 0;

    for (TradeServiceBase service : services) {
      service.init(commitInstructionCmd);

      maxCapital = Math.max(maxCapital, service.calculateRequiredCapital());
      maxPosition = Math.max(maxPosition, service.calculateRequiredPosition());
    }

    // 取大值向资金服务申请资源
  }

  // 获取指定投资服务下的所有可用交易服务
  public List<TradeServiceBase> getAvailableTradeServices(String investSvc) {
    List<TradeServiceBase> result = new ArrayList<TradeServiceBase>();

    Field[] fields = TradeServiceManager.class.getDeclaredFields();
    for (Field field : fields) {
      SupportedInvestService supportedInvestService =
          field.getType().getAnnotation(SupportedInvestService.class);
      if (supportedInvestService != null) {
        if ((investSvc == null)
            || (investSvc != null && supportedInvestService.value().equals(investSvc))) {
          try {
            Object o = field.get(this);
            result.add((TradeServiceBase) o);
          } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
          }
        }
      }
    }
    return result;
  }
}

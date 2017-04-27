package com.winsigns.investment.inventoryService.position.common;

import java.util.List;

import com.winsigns.investment.framework.service.IService;
import com.winsigns.investment.inventoryService.exception.ResourceApplicationExcepiton;
import com.winsigns.investment.inventoryService.model.PositionSerial;

/**
 * 持仓服务的接口
 * 
 * @author yimingjin
 *
 */
public interface IPositionService extends IService {

  /**
   * 投资服务申请持仓
   * 
   * @param portfolioId
   * @param securityId
   * @param type
   * @param quantity
   * @return
   * @throws ResourceApplicationExcepiton
   */
  List<PositionSerial> apply(Long portfolioId, Long securityId, String type, Long appliedPosition)
      throws ResourceApplicationExcepiton;
}

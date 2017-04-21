package com.winsigns.investment.inventoryService.position.common;

import com.winsigns.investment.framework.service.IService;
import com.winsigns.investment.inventoryService.exception.ResourceApplicationExcepiton;

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
   */
  void apply(Long portfolioId, Long securityId, String type, Long quantity)
      throws ResourceApplicationExcepiton;
}

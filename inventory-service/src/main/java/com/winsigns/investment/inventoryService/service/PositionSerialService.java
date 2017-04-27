package com.winsigns.investment.inventoryService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winsigns.investment.inventoryService.model.PositionSerial;
import com.winsigns.investment.inventoryService.repository.PositionSerialRepository;

@Service
public class PositionSerialService {

  @Autowired
  PositionSerialRepository positionSerialRepository;

  public PositionSerial addPositionSerial(Class<? extends PositionSerial> clazz, Long portfolioId,
      Long securityId, String type, Long etaId, Long occurQuantity) {
    PositionSerial positionSerial = null;
    try {
      positionSerial = clazz.newInstance();
    } catch (InstantiationException | IllegalAccessException e) {
      return null;
    }
    positionSerial.setPortfolioId(portfolioId);
    positionSerial.setSecurityId(securityId);
    positionSerial.setType(type);
    positionSerial.setExternalTradeAccountId(etaId);
    positionSerial.setOccurPosition(occurQuantity);
    return positionSerialRepository.save(positionSerial);
  }
}

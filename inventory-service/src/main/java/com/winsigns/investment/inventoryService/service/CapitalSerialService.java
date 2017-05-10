package com.winsigns.investment.inventoryService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winsigns.investment.framework.constant.CurrencyCode;
import com.winsigns.investment.inventoryService.model.CapitalSerial;
import com.winsigns.investment.inventoryService.repository.CapitalSerialRepository;

@Service
public class CapitalSerialService {

  @Autowired
  CapitalSerialRepository capitalSerialRepository;

  public CapitalSerial addCapitalSerial(Class<? extends CapitalSerial> clazz, Long srcId,
      Long dstId, CurrencyCode currency, Double occurAmount) {
    CapitalSerial capitalSerial = null;
    try {
      capitalSerial = clazz.newInstance();
    } catch (InstantiationException | IllegalAccessException e) {
      return null;
    }
    capitalSerial.setSourceAccountId(srcId);
    capitalSerial.setMatchAccountId(dstId);
    capitalSerial.setCurrency(currency);
    capitalSerial.setOccurAmount(occurAmount);
    return capitalSerialRepository.save(capitalSerial);
  }
}

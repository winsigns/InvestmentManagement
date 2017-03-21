package com.winsigns.investment.inventoryService.measure;

import static java.util.Arrays.asList;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winsigns.investment.framework.measure.ICalculateFactor;
import com.winsigns.investment.framework.measure.Measure;
import com.winsigns.investment.framework.measure.MeasureHostType;
import com.winsigns.investment.framework.measure.TradingMeasureValue;
import com.winsigns.investment.inventoryService.model.ECACashPool;
import com.winsigns.investment.inventoryService.model.ECACashSerial;
import com.winsigns.investment.inventoryService.repository.ECACashPoolRepository;
import com.winsigns.investment.inventoryService.repository.ECACashSerialRepository;

@Component
public class ECACashMeasure extends Measure {

  @Autowired
  ECACashPoolRepository ecaCashPoolRepository;

  @Autowired
  ECACashSerialRepository ecaCashSerialRepository;

  @Autowired
  ECACashPoolMHT ecaCashPoolMHT;

  @Override
  public MeasureHostType getSupportedHostType() {
    return ecaCashPoolMHT;
  }

  @Override
  public List<ICalculateFactor> getCalculateFactors() {
    return asList(new ECACashSerial());
  }

  @Override
  protected TradingMeasureValue doCalculate(Long measureHostId, boolean isFloat, String version) {

    ECACashPool ecaCashPool = ecaCashPoolRepository.findOne(measureHostId);

    Double cash = ecaCashSerialRepository.findByECACashPoolAndAssignedDate(ecaCashPool, new Date());

    return new TradingMeasureValue(ecaCashPool, this, isFloat, version, cash);

  }
}

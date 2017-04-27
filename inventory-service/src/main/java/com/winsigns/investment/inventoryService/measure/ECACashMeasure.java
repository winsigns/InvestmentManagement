package com.winsigns.investment.inventoryService.measure;

import static java.util.Arrays.asList;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winsigns.investment.framework.measure.IMeasure;
import com.winsigns.investment.framework.measure.Measure;
import com.winsigns.investment.framework.measure.MeasureHost;
import com.winsigns.investment.framework.measure.MeasureHostType;
import com.winsigns.investment.framework.measure.TradingMeasureValue;
import com.winsigns.investment.framework.model.OperatorEntity;
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
  public List<Class<? extends OperatorEntity>> getConcernedOperator() {
    return asList(ECACashSerial.class);
  }


  @Override
  public List<IMeasure> getDependentMeasure() {
    return null;
  }

  @Override
  protected TradingMeasureValue doCalculate(MeasureHost measureHost, boolean isFloat,
      String version) {
    ECACashPool ecaCashPool = (ECACashPool) measureHost;

    Double cash = ecaCashSerialRepository.findByECACashPoolAndAssignedDate(ecaCashPool, new Date());

    if (cash == null) {
      cash = 0.0;
    }

    return new TradingMeasureValue(ecaCashPool, this, isFloat, version, cash);
  }

}

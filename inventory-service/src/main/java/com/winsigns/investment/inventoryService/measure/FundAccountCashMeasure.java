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
import com.winsigns.investment.inventoryService.model.FundAccountCapitalDetail;
import com.winsigns.investment.inventoryService.model.FundAccountCapitalSerial;
import com.winsigns.investment.inventoryService.repository.FundAccountCapitalDetailRepository;
import com.winsigns.investment.inventoryService.repository.FundAccountCapitalSerialRepository;

@Component
public class FundAccountCashMeasure extends Measure {

  @Autowired
  FundAccountCapitalDetailRepository fundAccountCapitalDetailRepository;

  @Autowired
  FundAccountCapitalSerialRepository fundAccountCapitalSerialRepository;

  @Autowired
  FACapitalDetailMHT faCapitalDetailMHT;

  @Override
  public MeasureHostType getSupportedHostType() {
    return faCapitalDetailMHT;
  }

  @Override
  public List<ICalculateFactor> getCalculateFactors() {
    return asList(new FundAccountCapitalSerial());
  }

  @Override
  protected TradingMeasureValue doCalculate(Long measureHostId, boolean isFloat, String version) {

    FundAccountCapitalDetail fundAccountCapitalDetail =
        fundAccountCapitalDetailRepository.findOne(measureHostId);

    Double value = fundAccountCapitalSerialRepository
        .findByFundAccountCapitalDetailAndAssignedDate(fundAccountCapitalDetail, new Date());

    return new TradingMeasureValue(fundAccountCapitalDetail, this, isFloat, version, value);

  }
}

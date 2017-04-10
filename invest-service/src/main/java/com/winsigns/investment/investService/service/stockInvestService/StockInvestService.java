package com.winsigns.investment.investService.service.stockInvestService;

import org.springframework.stereotype.Service;

import com.winsigns.investment.framework.i18n.i18nHelper;
import com.winsigns.investment.investService.service.common.AbstractInvestService;

@Service
public class StockInvestService extends AbstractInvestService {

  public enum StockInvestType {

    BUY,

    SELL;

    public String i18n() {
      return i18nHelper.i18n(this);
    }
  }

  @Override
  public StockInvestType[] getInstructionType() {
    return StockInvestType.values();
  }

}

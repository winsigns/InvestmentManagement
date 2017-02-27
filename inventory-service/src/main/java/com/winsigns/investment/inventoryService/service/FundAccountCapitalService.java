package com.winsigns.investment.inventoryService.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winsigns.investment.inventoryService.command.CreateFundAccountCapitalCommand;
import com.winsigns.investment.inventoryService.command.SetInvestmentLimitCommand;
import com.winsigns.investment.inventoryService.model.FundAccountCapital;
import com.winsigns.investment.inventoryService.repository.FundAccountCapitalRepository;

@Service
public class FundAccountCapitalService {
  @Autowired
  private FundAccountCapitalRepository fundAccountCapitalRepository;

  public Collection<FundAccountCapital> findAll() {
    return fundAccountCapitalRepository.findAll();
  }

  public FundAccountCapital findOne(Long fundAccountCapitalId) {
    return fundAccountCapitalRepository.findOne(fundAccountCapitalId);
  }

  public FundAccountCapital addFundAccountCapital(
      CreateFundAccountCapitalCommand createFundAccountCapitalCommand) {
    FundAccountCapital fundAccountCapital =
        fundAccountCapitalRepository.findByFundAccountIdAndExternalCapitalAccountTypeAndCurrency(
            createFundAccountCapitalCommand.getFundAccountId(),
            createFundAccountCapitalCommand.getExternalCapitalAccountType(),
            createFundAccountCapitalCommand.getCurrency());

    if (fundAccountCapital == null) {
      fundAccountCapital = new FundAccountCapital();

      fundAccountCapital.setFundAccountId(createFundAccountCapitalCommand.getFundAccountId());
      fundAccountCapital.setExternalCapitalAccountType(
          createFundAccountCapitalCommand.getExternalCapitalAccountType());
      fundAccountCapital.setCurrency(createFundAccountCapitalCommand.getCurrency());
      fundAccountCapital = fundAccountCapitalRepository.save(fundAccountCapital);
    }
    return fundAccountCapital;
  }

  public FundAccountCapital setInvestmentLimit(Long faCapitalId,
      SetInvestmentLimitCommand setInvestmentLimitCommand) {
    FundAccountCapital fundAccountCapital = fundAccountCapitalRepository.findOne(faCapitalId);
    if (fundAccountCapital == null)
      return null;
    fundAccountCapital.setInvestmentLimit(setInvestmentLimitCommand.getInvestmentLimit());
    fundAccountCapital = fundAccountCapitalRepository.save(fundAccountCapital);
    return fundAccountCapital;
  }

}

package com.winsigns.investment.fundService.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.winsigns.investment.fundService.command.CreateFundAccountCommand;
import com.winsigns.investment.fundService.command.UpdateFundAccountCommand;
import com.winsigns.investment.fundService.model.Fund;
import com.winsigns.investment.fundService.model.FundAccount;
import com.winsigns.investment.fundService.model.InvestManager;
import com.winsigns.investment.fundService.repository.FundAccountRepository;
import com.winsigns.investment.fundService.repository.FundRepository;
import com.winsigns.investment.fundService.repository.InvestManagerRepository;

@Service
public class FundAccountService {

  @Autowired
  FundAccountRepository fundAccountRepository;

  @Autowired
  FundRepository fundRepository;

  @Autowired
  InvestManagerRepository investManagerRepository;

  public Collection<FundAccount> findByFundId(Long fundId) {
    return fundAccountRepository.findByFundId(fundId);
  }

  public Collection<FundAccount> findAll() {
    return fundAccountRepository.findAll();
  }

  public FundAccount addFundAccount(CreateFundAccountCommand createFundAccountCommand) {

    Fund fund = fundRepository.findOne(createFundAccountCommand.getFundId());

    if (fund == null) {
      return null;
    }

    FundAccount newFundAccount = new FundAccount();
    newFundAccount.setFund(fund);
    newFundAccount.setName(createFundAccountCommand.getName());

    if (createFundAccountCommand.getInvestManagerId() != null) {
      InvestManager investManager =
          investManagerRepository.findOne(createFundAccountCommand.getInvestManagerId());
      Assert.notNull(investManager);
      newFundAccount.setInvestManager(investManager);
    }

    return fundAccountRepository.save(newFundAccount);
  }

  public FundAccount updateFundAccount(Long fundAccountId,
      UpdateFundAccountCommand fundAccountCommand) {
    FundAccount fundAccount = fundAccountRepository.findOne(fundAccountId);

    if (fundAccount == null) {
      return null;
    }

    fundAccount.setName(fundAccountCommand.getName());

    if (fundAccountCommand.getInvestManagerId() != null) {
      InvestManager investManager =
          investManagerRepository.findOne(fundAccountCommand.getInvestManagerId());
      Assert.notNull(investManager);
      fundAccount.setInvestManager(investManager);
    }

    return fundAccountRepository.save(fundAccount);

  }

  public void deleteFundAccount(Long fundAccountId) {

    fundAccountRepository.delete(fundAccountId);
  }

  public FundAccount findOne(Long fundAccountId) {
    return fundAccountRepository.findOne(fundAccountId);
  }

}

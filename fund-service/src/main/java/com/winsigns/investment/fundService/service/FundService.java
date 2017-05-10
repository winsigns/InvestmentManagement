package com.winsigns.investment.fundService.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winsigns.investment.fundService.command.CreateFundCommand;
import com.winsigns.investment.fundService.command.UpdateFundCommand;
import com.winsigns.investment.fundService.model.Fund;
import com.winsigns.investment.fundService.model.FundAccount;
import com.winsigns.investment.fundService.model.InvestManager;
import com.winsigns.investment.fundService.repository.FundAccountRepository;
import com.winsigns.investment.fundService.repository.FundRepository;

@Service
public class FundService {

  @Autowired
  FundRepository fundRepository;

  @Autowired
  FundAccountRepository fundAccountRepository;

  public Fund addFund(CreateFundCommand fund) {

    Fund newFund = new Fund();

    newFund.setCode(fund.getCode());
    newFund.setTotalShares(fund.getTotalShares());
    newFund.setName(fund.getName());
    newFund.setShortName(fund.getShortName());

    return fundRepository.save(newFund);
  }

  public Fund updateFund(Long fundId, UpdateFundCommand fund) {
    Fund dstFund = fundRepository.findOne(fundId);

    if (dstFund == null) {
      return null;
    }

    dstFund.setCode(fund.getCode());
    dstFund.setTotalShares(fund.getTotalShares());
    dstFund.setName(fund.getName());
    dstFund.setShortName(fund.getShortName());

    return fundRepository.save(dstFund);
  }

  public void deleteFund(Long fundId) {
    fundRepository.delete(fundId);
  }


  public Fund findOne(Long fundId) {
    return fundRepository.findOne(fundId);
  }

  public List<Fund> findFunds() {
    return fundRepository.findAll();
  }

  public List<Fund> findFunds(InvestManager investManager) {


    if (investManager == null) {
      return null;
    }

    List<FundAccount> fundAccounts = fundAccountRepository.findByInvestManager(investManager);

    Set<Fund> funds = new HashSet<Fund>();

    for (FundAccount fundAccount : fundAccounts) {

      funds.add(fundAccount.getFund());

    }

    return new ArrayList<Fund>(funds);
  }


}

package com.winsigns.investment.fundService.service;

import com.winsigns.investment.fundService.command.CreateFundCommand;
import com.winsigns.investment.fundService.command.UpdateFundCommand;
import com.winsigns.investment.fundService.model.Fund;
import com.winsigns.investment.fundService.repository.FundRepository;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FundService {

  @Autowired
  FundRepository fundRepository;

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

  @Transactional
  public void deleteFund(Long fundId) {
    fundRepository.delete(fundId);
  }

  public Collection<Fund> findAllFunds() {
    return fundRepository.findAll();

  }

  public Fund findOne(Long fundId) {
    return fundRepository.findOne(fundId);
  }
}

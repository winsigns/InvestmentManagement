package com.winsigns.investment.fundService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winsigns.investment.fundService.model.InvestManager;
import com.winsigns.investment.fundService.repository.InvestManagerRepository;

@Service
public class InvestManagerService {


  @Autowired
  InvestManagerRepository investManagerRepository;


  public InvestManager findOne(Long investManagerId) {
    return investManagerRepository.findOne(investManagerId);
  }
}

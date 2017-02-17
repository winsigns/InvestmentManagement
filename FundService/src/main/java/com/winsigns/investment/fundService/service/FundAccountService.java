package com.winsigns.investment.fundService.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winsigns.investment.fundService.command.FundAccountCommand;
import com.winsigns.investment.fundService.model.Fund;
import com.winsigns.investment.fundService.model.FundAccount;
import com.winsigns.investment.fundService.repository.FundAccountRepository;
import com.winsigns.investment.fundService.repository.FundRepository;

@Service
public class FundAccountService {

	@Autowired
	FundAccountRepository fundAccountRepository;

	@Autowired
	FundRepository fundRepository;

	public Collection<FundAccount> findByFundId(Long fundId) {
		// TODO Auto-generated method stub
		return fundAccountRepository.findByFundId(fundId);
	}

	@Transactional
	public FundAccount addFundAccount(Long fundId, FundAccountCommand fundAccountCommand) {
		
		Fund fund = fundRepository.findOne(fundId);
		
		FundAccount newFundAccount = new FundAccount();
		newFundAccount.setFund(fund)
			.setName(fundAccountCommand.getName())
			.setInvestmentManager(fundAccountCommand.getInvestmentManager());
		
		return fundAccountRepository.save(newFundAccount);		
	}

	public FundAccount updateFundAccount(Long fundAccountId, FundAccountCommand fundAccountCommand) {
		FundAccount fundAccount = fundAccountRepository.findOne(fundAccountId);
		
		fundAccount
			.setName(fundAccountCommand.getName())
			.setInvestmentManager(fundAccountCommand.getInvestmentManager());
		
		return fundAccountRepository.save(fundAccount);
	}

	public void deleteFundAccount(Long fundAccountId) {
		
		fundAccountRepository.delete(fundAccountId);
	}
	
	public FundAccount findOne(Long fundAccountId){
		return fundAccountRepository.findOne(fundAccountId);
	}

}

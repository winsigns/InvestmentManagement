package com.winsigns.investment.fundService.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winsigns.investment.fundService.command.FundCommand;
import com.winsigns.investment.fundService.dictionary.Dictionaries;
import com.winsigns.investment.fundService.model.Fund;
import com.winsigns.investment.fundService.repository.FundRepository;

@Service
public class FundService {

	@Autowired
	FundRepository fundRepository;

	@Autowired
	Dictionaries dictionaries;

	@Transactional
	public Fund addFund(FundCommand fund) {

		Fund newFund = new Fund();

		newFund.setCode(fund.getCode());
		newFund.setTotalShares(fund.getTotalShares());
		newFund.setName(fund.getName());
		newFund.setShortName(fund.getShortName());

		return fundRepository.save(newFund);
	}

	@Transactional
	public Fund updateFund(Long fundId, FundCommand fund) {
		Fund dstFund = fundRepository.findOne(fundId);

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

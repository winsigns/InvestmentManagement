package com.winsigns.investment.fundService.service;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winsigns.investment.fundService.command.PortfolioCommand;
import com.winsigns.investment.fundService.model.FundAccount;
import com.winsigns.investment.fundService.model.Portfolio;
import com.winsigns.investment.fundService.repository.FundAccountRepository;
import com.winsigns.investment.fundService.repository.PortfolioRepository;

@Service
public class PortfolioService {

	@Autowired
	FundAccountRepository fundAccountRepository;

	@Autowired
	PortfolioRepository portfolioRepository;

	public Collection<Portfolio> findByFundAccountId(Long fundAccountId) {
		return portfolioRepository.findByFundAccountId(fundAccountId);
	}

	@Transactional
	public Portfolio addPortfolio(Long fundAccountId, PortfolioCommand portfolioCommand) {

		FundAccount fundAccount = fundAccountRepository.findOne(fundAccountId);

		Portfolio newPortfolio = new Portfolio();
		newPortfolio.setFundAccount(fundAccount);
		newPortfolio.setName(portfolioCommand.getName());
		newPortfolio.setCreateDate(new Date());
		return portfolioRepository.save(newPortfolio);
	}

	@Transactional
	public Portfolio updatePortfolio(Long portfolioId, PortfolioCommand portfolioCommand) {
		Portfolio portfolio = portfolioRepository.findOne(portfolioId);

		portfolio.setName(portfolioCommand.getName());
		return portfolioRepository.save(portfolio);
	}

	public void deletePortfolio(Long portfolioId) {

		portfolioRepository.delete(portfolioId);
	}

	public Portfolio findOne(Long portfolioId) {
		return portfolioRepository.findOne(portfolioId);
	}

}

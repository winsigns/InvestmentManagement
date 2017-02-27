package com.winsigns.investment.fundService.service;

import com.winsigns.investment.fundService.command.CreatePortfolioCommand;
import com.winsigns.investment.fundService.command.UpdatePortfolioCommand;
import com.winsigns.investment.fundService.model.FundAccount;
import com.winsigns.investment.fundService.model.Portfolio;
import com.winsigns.investment.fundService.repository.FundAccountRepository;
import com.winsigns.investment.fundService.repository.PortfolioRepository;
import java.util.Collection;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PortfolioService {

  @Autowired
  FundAccountRepository fundAccountRepository;

  @Autowired
  PortfolioRepository portfolioRepository;

  public Collection<Portfolio> findByFundAccountId(Long fundAccountId) {
    return portfolioRepository.findByFundAccountId(fundAccountId);
  }

  public Collection<Portfolio> findAll() {
    return portfolioRepository.findAll();
  }

  public Portfolio addPortfolio(CreatePortfolioCommand createPortfolioCommand) {

    FundAccount fundAccount = fundAccountRepository
        .findOne(createPortfolioCommand.getFundAccountId());

    if (fundAccount == null) {
      return null;
    }

    Portfolio newPortfolio = new Portfolio();
    newPortfolio.setFundAccount(fundAccount);
    newPortfolio.setName(createPortfolioCommand.getName());
    newPortfolio.setCreateDate(new Date());
    return portfolioRepository.save(newPortfolio);
  }

  public Portfolio updatePortfolio(Long portfolioId, UpdatePortfolioCommand portfolioCommand) {
    Portfolio portfolio = portfolioRepository.findOne(portfolioId);

    if (portfolio == null) {
      return null;
    }

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

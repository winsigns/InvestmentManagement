package com.winsigns.investment.fundService.service;

import com.winsigns.investment.fundService.command.CreateExternalCapitalAccountCommand;
import com.winsigns.investment.fundService.command.UpdateExternalCapitalAccountCommand;
import com.winsigns.investment.fundService.constant.ExternalCapitalAccountType;
import com.winsigns.investment.fundService.constant.ExternalOpenOrganization;
import com.winsigns.investment.fundService.model.ExternalCapitalAccount;
import com.winsigns.investment.fundService.model.Fund;
import com.winsigns.investment.fundService.repository.ExternalCapitalAccountRepository;
import com.winsigns.investment.fundService.repository.FundRepository;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExternalCapitalAccountService {

  @Autowired
  private ExternalCapitalAccountRepository externalCapitalAccountRepository;

  @Autowired
  private FundRepository fundRepository;

  public ExternalCapitalAccount findOne(Long externalCapitalAccountId) {
    return externalCapitalAccountRepository.findOne(externalCapitalAccountId);
  }

  public Collection<ExternalCapitalAccount> findByFundId(Long fundId) {
    return externalCapitalAccountRepository.findByFundId(fundId);
  }

  public Collection<ExternalCapitalAccount> findAll() {
    return externalCapitalAccountRepository.findAll();
  }

  public ExternalCapitalAccount addExternalCapitalAccount(
      CreateExternalCapitalAccountCommand createExternalCapitalAccountCommand) {

    Fund fund = fundRepository.findOne(createExternalCapitalAccountCommand.getFundId());

    if (fund == null) {
      return null;
    }

    ExternalCapitalAccount externalCapitalAccount = new ExternalCapitalAccount();
    externalCapitalAccount.setFund(fund);

    ExternalCapitalAccountType externalCapitalAccountType = createExternalCapitalAccountCommand
        .getAccountType();

    ExternalOpenOrganization externalOpenOrganization = createExternalCapitalAccountCommand
        .getExternalOpenOrganization();

    externalCapitalAccount.setAccountType(externalCapitalAccountType);
    externalCapitalAccount.setAccountNo(createExternalCapitalAccountCommand.getAccountNo());
    externalCapitalAccount.setExternalOpenOrganization(externalOpenOrganization);

    return externalCapitalAccountRepository.save(externalCapitalAccount);
  }

  public ExternalCapitalAccount updateExternalCapitalAccount(Long externalCapitalAccountId,
      UpdateExternalCapitalAccountCommand externalCapitalAccountCommand) {

    ExternalCapitalAccount externalCapitalAccount = externalCapitalAccountRepository
        .findOne(externalCapitalAccountId);

    if (externalCapitalAccount == null) {
      return null;
    }

    ExternalCapitalAccountType externalCapitalAccountType = externalCapitalAccountCommand
        .getAccountType();

    ExternalOpenOrganization externalOpenOrganization = externalCapitalAccountCommand
        .getExternalOpenOrganization();

    externalCapitalAccount.setAccountType(externalCapitalAccountType);
    externalCapitalAccount.setAccountNo(externalCapitalAccountCommand.getAccountNo());
    externalCapitalAccount.setExternalOpenOrganization(externalOpenOrganization);

    return externalCapitalAccountRepository.save(externalCapitalAccount);
  }

  public void deleteExternalCapitalAccount(Long externalCapitalAccountId) {
    externalCapitalAccountRepository.delete(externalCapitalAccountId);
  }

}

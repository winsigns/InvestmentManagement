package com.winsigns.investment.fundService.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winsigns.investment.fundService.command.CreateExternalTradeAccountCommand;
import com.winsigns.investment.fundService.command.UpdateExternalTradeAccountCommand;
import com.winsigns.investment.fundService.model.ExternalCapitalAccount;
import com.winsigns.investment.fundService.model.ExternalTradeAccount;
import com.winsigns.investment.fundService.repository.ExternalCapitalAccountRepository;
import com.winsigns.investment.fundService.repository.ExternalTradeAccountRepository;

@Service
public class ExternalTradeAccountService {

  @Autowired
  private ExternalCapitalAccountRepository externalCapitalAccountRepository;

  @Autowired
  private ExternalTradeAccountRepository externalTradeAccountRepository;

  public ExternalTradeAccount findOne(Long externalTradeAccountId) {
    return externalTradeAccountRepository.findOne(externalTradeAccountId);
  }

  public Collection<ExternalTradeAccount> findByExternalCapitalAccountId(
      Long externalCapitalAccountId) {
    return externalTradeAccountRepository.findByExternalCapitalAccountId(externalCapitalAccountId);
  }

  public Collection<ExternalTradeAccount> findAll() {
    return externalTradeAccountRepository.findAll();
  }

  public ExternalTradeAccount addExternalTradeAccount(Long externalCapitalAccountId,
      CreateExternalTradeAccountCommand createExternalTradeAccountCommand) {

    ExternalCapitalAccount externalCapitalAccount =
        externalCapitalAccountRepository.findOne(externalCapitalAccountId);

    if (externalCapitalAccount == null) {
      return null;
    }

    ExternalTradeAccount externalTradeAccount = new ExternalTradeAccount();

    externalTradeAccount.setExternalCapitalAccount(externalCapitalAccount);

    externalTradeAccount.setAccountNo(createExternalTradeAccountCommand.getAccountNo());
    externalTradeAccount.setAccountType(createExternalTradeAccountCommand.getAccountType());

    return externalTradeAccountRepository.save(externalTradeAccount);
  }

  public ExternalTradeAccount updateExternalTradeAccount(Long externalTradeAccountId,
      UpdateExternalTradeAccountCommand externalTradeAccountCommand) {

    ExternalTradeAccount externalTradeAccount =
        externalTradeAccountRepository.findOne(externalTradeAccountId);

    if (externalTradeAccount == null) {
      return null;
    }

    externalTradeAccount.setAccountNo(externalTradeAccountCommand.getAccountNo());
    externalTradeAccount.setAccountType(externalTradeAccountCommand.getAccountType());

    return externalTradeAccountRepository.save(externalTradeAccount);
  }

  public void deleteExternalTradeAccount(Long externalTradeAccountId) {
    externalTradeAccountRepository.delete(externalTradeAccountId);
  }

}

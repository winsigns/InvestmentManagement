package com.winsigns.investment.fundService.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winsigns.investment.fundService.command.ExternalTradeAccountCommand;
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

    public Collection<ExternalTradeAccount> findByExternalCapitalAccountId(Long externalCapitalAccountId) {
        return externalTradeAccountRepository.findByExternalCapitalAccountId(externalCapitalAccountId);
    }

    public ExternalTradeAccount addExternalTradeAccount(Long fundId, Long externalCapitalAccountId,
            ExternalTradeAccountCommand externalTradeAccountCommand) {

        ExternalTradeAccount externalTradeAccount = new ExternalTradeAccount();

        externalTradeAccount
                .setExternalCapitalAccount(externalCapitalAccountRepository.findOne(externalCapitalAccountId));

        externalTradeAccount.setExternalTradeAccount(externalTradeAccountCommand.getExternalTradeAccount());
        externalTradeAccount.setExternalTradeAccountType(externalTradeAccountCommand.getExternalTradeAccountType());

        return externalTradeAccountRepository.save(externalTradeAccount);
    }

    public ExternalTradeAccount updateExternalTradeAccount(Long externalTradeAccountId,
            ExternalTradeAccountCommand externalTradeAccountCommand) {

        externalTradeAccount.setAccountNo(createExternalTradeAccountCommand.getAccountNo());
        externalTradeAccount
                .setExternalTradeAccountType(createExternalTradeAccountCommand.getExternalTradeAccountType());

        externalTradeAccount.setExternalTradeAccount(externalTradeAccountCommand.getExternalTradeAccount());
        externalTradeAccount.setExternalTradeAccountType(externalTradeAccountCommand.getExternalTradeAccountType());

        return externalTradeAccountRepository.save(externalTradeAccount);
    }

    ExternalTradeAccount externalTradeAccount = externalTradeAccountRepository.findOne(externalTradeAccountId);

    if(externalTradeAccount==null)return null;

    externalTradeAccount.setAccountNo(externalTradeAccountCommand.getAccountNo());externalTradeAccount.setExternalTradeAccountType(externalTradeAccountCommand.getExternalTradeAccountType());

    return externalTradeAccountRepository.save(externalTradeAccount);
    }

    public void deleteExternalTradeAccount(Long externalTradeAccountId) {
        externalTradeAccountRepository.delete(externalTradeAccountId);
    }

}

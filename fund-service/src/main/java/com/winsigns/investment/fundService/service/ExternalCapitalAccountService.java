package com.winsigns.investment.fundService.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winsigns.investment.fundService.command.CreateExternalCapitalAccountCommand;
import com.winsigns.investment.fundService.command.UpdateExternalCapitalAccountCommand;
import com.winsigns.investment.fundService.constant.ExternalCapitalAccountType;
import com.winsigns.investment.fundService.constant.ExternalOpenOrganization;
import com.winsigns.investment.fundService.model.ExternalCapitalAccount;
import com.winsigns.investment.fundService.model.Fund;
import com.winsigns.investment.fundService.repository.ExternalCapitalAccountRepository;
import com.winsigns.investment.fundService.repository.FundRepository;

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

        if (fund == null)
            return null;

        ExternalCapitalAccount externalCapitalAccount = new ExternalCapitalAccount();
        externalCapitalAccount.setFund(fund);

        ExternalCapitalAccountType externalCapitalAccountType = createExternalCapitalAccountCommand
                .getExternalCapitalAccountType();

        ExternalOpenOrganization externalOpenOrganization = createExternalCapitalAccountCommand
                .getExternalOpenOrganization();

        externalCapitalAccount.setExternalCapitalAccountType(externalCapitalAccountType);
        externalCapitalAccount
        externalCapitalAccount.setExternalOpenOrganization(externalOpenOrganization);

        return externalCapitalAccountRepository.save(externalCapitalAccount);
    }

    public ExternalCapitalAccount updateExternalCapitalAccount(Long externalCapitalAccountId,
            UpdateExternalCapitalAccountCommand externalCapitalAccountCommand) {

        ExternalCapitalAccount externalCapitalAccount = externalCapitalAccountRepository
                .findOne(externalCapitalAccountId);

        if (externalCapitalAccount == null)
            return null;

        ExternalCapitalAccountType externalCapitalAccountType = externalCapitalAccountCommand
                .getExternalCapitalAccountType();

        ExternalOpenOrganization externalOpenOrganization = externalCapitalAccountCommand.getExternalOpenOrganization();

        externalCapitalAccount.setExternalCapitalAccountType(externalCapitalAccountType);
        externalCapitalAccount.setExternalOpenOrganization(externalOpenOrganization);

        return externalCapitalAccountRepository.save(externalCapitalAccount);
    }

    public void deleteExternalCapitalAccount(Long externalCapitalAccountId) {
        externalCapitalAccountRepository.delete(externalCapitalAccountId);
    }

}

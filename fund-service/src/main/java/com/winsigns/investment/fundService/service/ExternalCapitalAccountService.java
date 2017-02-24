package com.winsigns.investment.fundService.service;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Currency;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.winsigns.investment.fundService.command.CashChangeCommand;
import com.winsigns.investment.fundService.command.ExternalCapitalAccountCommand;
import com.winsigns.investment.fundService.constant.ExternalCapitalAccountType;
import com.winsigns.investment.fundService.constant.ExternalOpenOrganization;
import com.winsigns.investment.fundService.controller.ExternalCapitalAccountController;
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

    @Transactional
    public ExternalCapitalAccount addExternalCapitalAccount(Long fundId,
            ExternalCapitalAccountCommand externalCapitalAccountCommand) {

        Fund fund = fundRepository.findOne(fundId);

        ExternalCapitalAccount externalCapitalAccount = new ExternalCapitalAccount();
        externalCapitalAccount.setFund(fund);

        ExternalCapitalAccountType externalCapitalAccountType = externalCapitalAccountCommand
                .getExternalCapitalAccountType();

        ExternalOpenOrganization externalOpenOrganization = externalCapitalAccountCommand.getExternalOpenOrganization();

        externalCapitalAccount.setExternalCapitalAccountType(externalCapitalAccountType);
        externalCapitalAccount.setExternalCapitalAccount(externalCapitalAccountCommand.getExternalCapitalAccount());
        externalCapitalAccount.setExternalOpenOrganization(externalOpenOrganization);
        externalCapitalAccount = externalCapitalAccountRepository.save(externalCapitalAccount);

        RestTemplate restTemplate = new RestTemplate();

        String url = linkTo(ExternalCapitalAccountController.class, fundId)
                .slash(externalCapitalAccount.getId() + "/eCACashPools").toUri().getPath();
        CashChangeCommand cashChangeCommand = new CashChangeCommand();

        for (Currency currency : externalCapitalAccount.getExternalCapitalAccountType().getSupportedCurrency()) {
            cashChangeCommand.setCurrency(currency);
            HttpEntity<CashChangeCommand> requestEntity = new HttpEntity<CashChangeCommand>(cashChangeCommand);
            try {
                restTemplate.postForEntity(new URI("http://localhost:10011" + url), requestEntity, String.class);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        return externalCapitalAccount;
    }

    public void deleteExternalCapitalAccount(Long externalCapitalAccountId) {
        externalCapitalAccountRepository.delete(externalCapitalAccountId);
    }

    public ExternalCapitalAccount updateExternalCapitalAccount(Long externalCapitalAccountId,
            ExternalCapitalAccountCommand externalCapitalAccountCommand) {

        ExternalCapitalAccount externalCapitalAccount = externalCapitalAccountRepository
                .findOne(externalCapitalAccountId);

        ExternalCapitalAccountType externalCapitalAccountType = externalCapitalAccountCommand
                .getExternalCapitalAccountType();

        ExternalOpenOrganization externalOpenOrganization = externalCapitalAccountCommand.getExternalOpenOrganization();

        externalCapitalAccount.setExternalCapitalAccountType(externalCapitalAccountType);
        externalCapitalAccount.setExternalCapitalAccount(externalCapitalAccountCommand.getExternalCapitalAccount());
        externalCapitalAccount.setExternalOpenOrganization(externalOpenOrganization);

        return externalCapitalAccountRepository.save(externalCapitalAccount);
    }

}

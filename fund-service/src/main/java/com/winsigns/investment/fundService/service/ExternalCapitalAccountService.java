package com.winsigns.investment.fundService.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winsigns.investment.fundService.command.ExternalCapitalAccountCommand;
import com.winsigns.investment.fundService.model.ExternalCapitalAccount;
import com.winsigns.investment.fundService.model.ExternalCapitalAccountType;
import com.winsigns.investment.fundService.model.ExternalOpenOrganization;
import com.winsigns.investment.fundService.model.Fund;
import com.winsigns.investment.fundService.repository.ExternalCapitalAccountRepository;
import com.winsigns.investment.fundService.repository.ExternalCapitalAccountTypeRepository;
import com.winsigns.investment.fundService.repository.ExternalOpenOrganizationRepository;
import com.winsigns.investment.fundService.repository.FundRepository;

@Service
public class ExternalCapitalAccountService {

	@Autowired
	private ExternalCapitalAccountTypeRepository externalCapitalAccountTypeRepository;

	@Autowired
	private ExternalCapitalAccountRepository externalCapitalAccountRepository;

	@Autowired
	private ExternalOpenOrganizationRepository externalOpenOrganizationRepository;

	@Autowired
	private FundRepository fundRepository;

	public ExternalCapitalAccount findOne(Long externalCapitalAccountId) {
		return externalCapitalAccountRepository.findOne(externalCapitalAccountId);
	}

	public Collection<ExternalCapitalAccount> findByFundId(Long fundId) {
		return externalCapitalAccountRepository.findByFundId(fundId);
	}

	public ExternalCapitalAccount addExternalCapitalAccount(Long fundId,
			ExternalCapitalAccountCommand externalCapitalAccountCommand) {

		Fund fund = fundRepository.findOne(fundId);

		ExternalCapitalAccount externalCapitalAccount = new ExternalCapitalAccount();
		externalCapitalAccount.setFund(fund);

		ExternalCapitalAccountType externalCapitalAccountType = externalCapitalAccountTypeRepository
				.findOne(externalCapitalAccountCommand.getExternalCapitalAccountTypeId());

		ExternalOpenOrganization externalOpenOrganization = externalOpenOrganizationRepository
				.findOne(externalCapitalAccountCommand.getExternalOpenOrganizationId());

		externalCapitalAccount.setExternalCapitalAccountType(externalCapitalAccountType);
		externalCapitalAccount.setExternalCapitalAccount(externalCapitalAccountCommand.getExternalCapitalAccount());
		externalCapitalAccount.setExternalOpenOrganization(externalOpenOrganization);

		return externalCapitalAccountRepository.save(externalCapitalAccount);
	}

	public void deleteExternalCapitalAccount(Long externalCapitalAccountId) {
		externalCapitalAccountRepository.delete(externalCapitalAccountId);
	}

	public ExternalCapitalAccount updateExternalCapitalAccount(Long externalCapitalAccountId,
			ExternalCapitalAccountCommand externalCapitalAccountCommand) {

		ExternalCapitalAccount externalCapitalAccount = externalCapitalAccountRepository
				.findOne(externalCapitalAccountId);

		ExternalCapitalAccountType externalCapitalAccountType = externalCapitalAccountTypeRepository
				.findOne(externalCapitalAccountCommand.getExternalCapitalAccountTypeId());

		ExternalOpenOrganization externalOpenOrganization = externalOpenOrganizationRepository
				.findOne(externalCapitalAccountCommand.getExternalOpenOrganizationId());

		externalCapitalAccount.setExternalCapitalAccountType(externalCapitalAccountType);
		externalCapitalAccount.setExternalCapitalAccount(externalCapitalAccountCommand.getExternalCapitalAccount());
		externalCapitalAccount.setExternalOpenOrganization(externalOpenOrganization);

		return externalCapitalAccountRepository.save(externalCapitalAccount);
	}

}
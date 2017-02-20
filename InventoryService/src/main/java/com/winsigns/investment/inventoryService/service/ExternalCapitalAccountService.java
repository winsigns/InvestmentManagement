package com.winsigns.investment.inventoryService.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winsigns.investment.inventoryService.command.ExternalCapitalAccountCommand;
import com.winsigns.investment.inventoryService.model.ExternalCapitalAccount;
import com.winsigns.investment.inventoryService.model.ExternalCapitalAccountType;
import com.winsigns.investment.inventoryService.model.ExternalOpenOrganization;
import com.winsigns.investment.inventoryService.repository.ExternalCapitalAccountRepository;
import com.winsigns.investment.inventoryService.repository.ExternalCapitalAccountTypeRepository;
import com.winsigns.investment.inventoryService.repository.ExternalOpenOrganizationRepository;

@Service
public class ExternalCapitalAccountService {

	@Autowired
	private ExternalCapitalAccountTypeRepository externalCapitalAccountTypeRepository;

	@Autowired
	private ExternalCapitalAccountRepository externalCapitalAccountRepository;

	@Autowired
	private ExternalOpenOrganizationRepository externalOpenOrganizationRepository;

	public ExternalCapitalAccount findOne(Long externalCapitalAccountId) {
		return externalCapitalAccountRepository.findOne(externalCapitalAccountId);
	}

	public Collection<ExternalCapitalAccount> findByFundId(Long fundId) {
		return externalCapitalAccountRepository.findByFundId(fundId);
	}

	public ExternalCapitalAccount addExternalCapitalAccount(Long fundId,
			ExternalCapitalAccountCommand externalCapitalAccountCommand) {

		// TODO check fundId

		ExternalCapitalAccount externalCapitalAccount = new ExternalCapitalAccount();
		externalCapitalAccount.setFundId(fundId);

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

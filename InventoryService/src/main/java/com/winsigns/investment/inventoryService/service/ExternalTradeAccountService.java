package com.winsigns.investment.inventoryService.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winsigns.investment.inventoryService.command.ExternalTradeAccountCommand;
import com.winsigns.investment.inventoryService.model.ExternalTradeAccount;
import com.winsigns.investment.inventoryService.model.ExternalTradeAccountType;
import com.winsigns.investment.inventoryService.model.InvestmentScope;
import com.winsigns.investment.inventoryService.repository.ExternalCapitalAccountRepository;
import com.winsigns.investment.inventoryService.repository.ExternalTradeAccountRepository;
import com.winsigns.investment.inventoryService.repository.ExternalTradeAccountTypeRepository;
import com.winsigns.investment.inventoryService.repository.InvestmentScopeRepository;

@Service
public class ExternalTradeAccountService {

	@Autowired
	private ExternalCapitalAccountRepository externalCapitalAccountRepository;

	@Autowired
	private ExternalTradeAccountTypeRepository externalTradeAccountTypeRepository;

	@Autowired
	private ExternalTradeAccountRepository externalTradeAccountRepository;

	@Autowired
	private InvestmentScopeRepository investmentScopeRepository;

	public ExternalTradeAccount findOne(Long externalTradeAccountId) {
		return externalTradeAccountRepository.findOne(externalTradeAccountId);
	}

	public Collection<ExternalTradeAccount> findByExternalCapitalAccountId(Long externalCapitalAccountId) {
		return externalTradeAccountRepository.findByExternalCapitalAccountId(externalCapitalAccountId);
	}

	public ExternalTradeAccount addExternalTradeAccount(Long fundId, Long externalCapitalAccountId,
			ExternalTradeAccountCommand externalTradeAccountCommand) {

		// TODO check fundId

		ExternalTradeAccount externalTradeAccount = new ExternalTradeAccount();

		externalTradeAccount
				.setExternalCapitalAccount(externalCapitalAccountRepository.findOne(externalCapitalAccountId));

		externalTradeAccount.setExternalTradeAccount(externalTradeAccountCommand.getExternalTradeAccount());

		ExternalTradeAccountType externalTradeAccountType = externalTradeAccountTypeRepository
				.findOne(externalTradeAccountCommand.getExtTradeAccountTypeId());
		externalTradeAccount.setExternalTradeAccountType(externalTradeAccountType);

		List<InvestmentScope> openedInvestmentScopes = new ArrayList<InvestmentScope>();
		if (externalTradeAccountCommand.getOpenedInvestmentScopeIds() == null) {

		} else {
			for (Long investmentScopeId : externalTradeAccountCommand.getOpenedInvestmentScopeIds()) {
				openedInvestmentScopes.add(investmentScopeRepository.findOne(investmentScopeId));
			}
		}
		externalTradeAccount.setOpenedInvestmentScopes(openedInvestmentScopes);

		return externalTradeAccountRepository.save(externalTradeAccount);
	}

	public ExternalTradeAccount updateExternalTradeAccount(Long externalTradeAccountId,
			ExternalTradeAccountCommand externalTradeAccountCommand) {

		ExternalTradeAccount externalTradeAccount = externalTradeAccountRepository.findOne(externalTradeAccountId);

		externalTradeAccount.setExternalTradeAccount(externalTradeAccountCommand.getExternalTradeAccount());

		ExternalTradeAccountType externalTradeAccountType = externalTradeAccountTypeRepository
				.findOne(externalTradeAccountCommand.getExtTradeAccountTypeId());
		externalTradeAccount.setExternalTradeAccountType(externalTradeAccountType);

		List<InvestmentScope> openedInvestmentScopes = new ArrayList<InvestmentScope>();
		if (externalTradeAccountCommand.getOpenedInvestmentScopeIds() == null) {

		} else {
			for (Long investmentScopeId : externalTradeAccountCommand.getOpenedInvestmentScopeIds()) {
				openedInvestmentScopes.add(investmentScopeRepository.findOne(investmentScopeId));
			}
		}
		externalTradeAccount.setOpenedInvestmentScopes(openedInvestmentScopes);

		return externalTradeAccountRepository.save(externalTradeAccount);
	}

	public void deleteExternalTradeAccount(Long externalTradeAccountId) {
		externalTradeAccountRepository.delete(externalTradeAccountId);
	}

}

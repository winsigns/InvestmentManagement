package com.winsigns.investment.fundService.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winsigns.investment.fundService.command.ExternalTradeAccountCommand;
import com.winsigns.investment.fundService.model.ExternalTradeAccount;
import com.winsigns.investment.fundService.model.ExternalTradeAccountType;
import com.winsigns.investment.fundService.model.OpenedInvestmentScope;
import com.winsigns.investment.fundService.repository.ExternalCapitalAccountRepository;
import com.winsigns.investment.fundService.repository.ExternalTradeAccountRepository;
import com.winsigns.investment.fundService.repository.ExternalTradeAccountTypeRepository;
import com.winsigns.investment.fundService.repository.InvestmentScopeRepository;

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

		ExternalTradeAccount externalTradeAccount = new ExternalTradeAccount();

		externalTradeAccount
				.setExternalCapitalAccount(externalCapitalAccountRepository.findOne(externalCapitalAccountId));

		externalTradeAccount.setExternalTradeAccount(externalTradeAccountCommand.getExternalTradeAccount());

		ExternalTradeAccountType externalTradeAccountType = externalTradeAccountTypeRepository
				.findOne(externalTradeAccountCommand.getExtTradeAccountTypeId());
		externalTradeAccount.setExternalTradeAccountType(externalTradeAccountType);

		List<OpenedInvestmentScope> openedInvestmentScopes = new ArrayList<OpenedInvestmentScope>();
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

		List<OpenedInvestmentScope> openedInvestmentScopes = new ArrayList<OpenedInvestmentScope>();
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
